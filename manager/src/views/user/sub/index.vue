<template>
  <el-card>
    <el-row justify="space-between" align="middle" class="mb-4">
      <el-col>
        <el-input
          v-model="searchKeyword"
          placeholder="请输入子账号名称"
          clearable
          @clear="fetchSubAccounts"
          @keyup.enter="fetchSubAccounts"
          style="width: 250px"
        />
      </el-col>
      <el-col class="text-right">
        <el-button type="primary" @click="openAddDialog">新增子账号</el-button>
      </el-col>
    </el-row>

    <el-table :data="subAccounts" border stripe style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="role" label="角色" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'error'">
            {{ row.status == 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button size="small" @click="openEditDialog(row)">编辑</el-button>
          <el-button size="small" @click="openPermissionDialog(row)">权限</el-button>
          <el-button size="small" type="danger" @click="deleteSubAccount(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      class="mt-4"
      background
      layout="prev, pager, next"
      :total="total"
      :page-size="pageSize"
      :current-page="currentPage"
      @current-change="handlePageChange"
    />

    <!-- 添加/编辑子账号对话框 -->
    <el-dialog
      :title="isEditing ? '编辑子账号' : '新增子账号'"
      v-model="dialogVisible"
      width="500px"
    >
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="form.role" placeholder="请选择角色">
            <el-option
              v-for="role in roleOptions"
              :key="role.value"
              :label="role.label"
              :value="role.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch
            v-model="form.status"
            active-text="启用"
            inactive-text="禁用"
            :active-value="1"
            :inactive-value="0"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>

    <!-- 权限设置对话框 -->
    <el-dialog title="权限设置" v-model="permissionDialogVisible" width="700px">
      <div class="mb-4">
        <el-tag type="info">当前用户: {{ currentAccount.username }}</el-tag>
        <el-tag type="info" class="ml-2">角色: {{ currentAccount.role }}</el-tag>
      </div>

      <el-tabs type="border-card">
        <el-tab-pane v-for="module in permissionModules" :key="module.name" :label="module.label">
          <el-table :data="module.permissions" border style="width: 100%">
            <el-table-column prop="name" label="权限名称" width="180" />
            <el-table-column prop="description" label="描述" />
            <el-table-column label="状态" width="120">
              <template #default="{ row }">
                <el-switch v-model="row.enabled" @change="handlePermissionChange(row)" />
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>

      <template #footer>
        <el-button @click="permissionDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

interface SubAccount {
  id: number
  username: string
  email: string
  role: string
  status: number // 0: 禁用, 1: 启用
  permissions?: Permission[]
}

interface Permission {
  id: string
  name: string
  description: string
  enabled: boolean
}

interface PermissionModule {
  name: string
  label: string
  permissions: Permission[]
}

const subAccounts = ref<SubAccount[]>([])
const total = ref(0)
const pageSize = ref(10)
const currentPage = ref(1)
const searchKeyword = ref('')

const dialogVisible = ref(false)
const permissionDialogVisible = ref(false)
const isEditing = ref(false)
const form = reactive<Partial<SubAccount>>({})
const formRef = ref()
const currentAccount = reactive<SubAccount>({
  id: 0,
  username: '',
  email: '',
  role: '',
  status: 0,
})

const roleOptions = [
  { value: 'super_admin', label: '超级管理员' },
  { value: 'admin', label: '管理员' },
  { value: 'editor', label: '编辑' },
  { value: 'viewer', label: '查看者' },
]

// 权限模块配置
const permissionModules = ref<PermissionModule[]>([
  {
    name: 'system',
    label: '系统管理',
    permissions: [
      { id: 'system_user', name: '用户管理', description: '管理系统用户', enabled: false },
      { id: 'system_role', name: '角色管理', description: '管理系统角色', enabled: false },
      { id: 'system_setting', name: '系统设置', description: '修改系统设置', enabled: false },
    ],
  },
  {
    name: 'device',
    label: '设备管理',
    permissions: [
      { id: 'device_view', name: '查看设备', description: '查看设备列表', enabled: false },
      { id: 'device_edit', name: '编辑设备', description: '添加/编辑设备', enabled: false },
      { id: 'device_delete', name: '删除设备', description: '删除设备', enabled: false },
    ],
  },
  {
    name: 'toilet',
    label: '厕所管理',
    permissions: [
      { id: 'toilet_view', name: '查看厕所', description: '查看厕所信息', enabled: false },
      { id: 'toilet_edit', name: '编辑厕所', description: '添加/编辑厕所', enabled: false },
      { id: 'toilet_stats', name: '查看统计', description: '查看厕所使用统计', enabled: false },
    ],
  },
  {
    name: 'report',
    label: '报表管理',
    permissions: [
      { id: 'report_view', name: '查看报表', description: '查看各类报表', enabled: false },
      { id: 'report_export', name: '导出报表', description: '导出报表数据', enabled: false },
    ],
  },
])

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur', 'change'] },
  ],
  role: [{ required: true, message: '请选择角色', trigger: 'change' }],
}

const fetchSubAccounts = () => {
  // 模拟API调用
  setTimeout(() => {
    subAccounts.value = [
      {
        id: 1,
        username: 'admin',
        email: 'admin@example.com',
        role: 'super_admin',
        status: 1,
        permissions: [
          { id: 'system_user', name: '用户管理', description: '管理系统用户', enabled: true },
          { id: 'system_role', name: '角色管理', description: '管理系统角色', enabled: true },
          { id: 'device_view', name: '查看设备', description: '查看设备列表', enabled: true },
        ],
      },
      {
        id: 2,
        username: 'editor1',
        email: 'editor1@example.com',
        role: 'editor',
        status: 1,
        permissions: [
          { id: 'device_view', name: '查看设备', description: '查看设备列表', enabled: true },
          { id: 'device_edit', name: '编辑设备', description: '添加/编辑设备', enabled: true },
        ],
      },
      {
        id: 3,
        username: 'viewer1',
        email: 'viewer1@example.com',
        role: 'viewer',
        status: 0,
        permissions: [
          { id: 'device_view', name: '查看设备', description: '查看设备列表', enabled: true },
        ],
      },
    ]
    total.value = subAccounts.value.length
  }, 500)
}

const handlePageChange = (page: number) => {
  currentPage.value = page
  fetchSubAccounts()
}

const openAddDialog = () => {
  isEditing.value = false
  Object.assign(form, { username: '', email: '', role: '', status: 1 })
  dialogVisible.value = true
}

const openEditDialog = (account: SubAccount) => {
  isEditing.value = true
  Object.assign(form, account)
  dialogVisible.value = true
}

const openPermissionDialog = (account: SubAccount) => {
  Object.assign(currentAccount, account)

  // 重置所有权限为禁用状态
  permissionModules.value.forEach((module) => {
    module.permissions.forEach((permission) => {
      permission.enabled = false
    })
  })

  // 设置用户已有的权限
  if (account.permissions && account.permissions.length > 0) {
    account.permissions.forEach((userPerm) => {
      permissionModules.value.forEach((module) => {
        const found = module.permissions.find((p) => p.id === userPerm.id)
        if (found) {
          found.enabled = userPerm.enabled
        }
      })
    })
  }

  permissionDialogVisible.value = true
}

const handlePermissionChange = (permission: Permission) => {
  ElMessageBox.confirm(
    `确定要${permission.enabled ? '开启' : '关闭'}【${permission.name}】权限吗?`,
    '权限变更确认',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    },
  )
    .then(() => {
      // TODO: 调用API更新权限
      ElMessage.success('权限更新成功')
    })
    .catch(() => {
      // 取消操作，恢复原来的状态
      permission.enabled = !permission.enabled
    })
}

const submitForm = () => {
  formRef.value.validate((valid: boolean) => {
    if (valid) {
      if (isEditing.value) {
        // TODO: 调用更新子账号的 API
        ElMessage.success('子账号更新成功')
      } else {
        // TODO: 调用添加子账号的 API
        ElMessage.success('子账号添加成功')
      }
      dialogVisible.value = false
      fetchSubAccounts()
    }
  })
}

const deleteSubAccount = (id: number) => {
  ElMessageBox.confirm('确定要删除该子账号吗？', '提示', {
    type: 'warning',
  })
    .then(() => {
      // TODO: 调用删除子账号的 API
      ElMessage.success('子账号删除成功')
      fetchSubAccounts()
    })
    .catch(() => {
      // 用户取消删除
    })
}

onMounted(() => {
  fetchSubAccounts()
})
</script>

<style scoped>
.mb-4 {
  margin-bottom: 16px;
}

.mt-4 {
  margin-top: 16px;
}

.ml-2 {
  margin-left: 8px;
}
</style>
