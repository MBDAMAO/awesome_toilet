<template>
  <el-card>
    <el-row justify="space-between" align="middle" class="mb-4">
      <el-col>
        <el-input v-model="searchKeyword" placeholder="请输入子账号名称" clearable @clear="fetchSubAccounts"
          @keyup.enter="fetchSubAccounts" style="width: 250px;" />
      </el-col>
      <el-col>
        <el-button type="primary" @click="openAddDialog">新增子账号</el-button>
      </el-col>
    </el-row>

    <el-table :data="subAccounts" border stripe style="width: 100%;">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="role" label="角色" />
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="row.status === '启用' ? 'success' : 'info'">
            {{ row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button size="small" @click="openEditDialog(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteSubAccount(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination class="mt-4" background layout="prev, pager, next" :total="total" :page-size="pageSize"
      :current-page="currentPage" @current-change="handlePageChange" />

    <!-- 添加/编辑子账号对话框 -->
    <el-dialog :title="isEditing ? '编辑子账号' : '新增子账号'" :visible.sync="dialogVisible" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="form.role" placeholder="请选择角色">
            <el-option label="管理员" value="管理员" />
            <el-option label="编辑" value="编辑" />
            <el-option label="访客" value="访客" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch v-model="form.status" active-text="启用" inactive-text="禁用" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';

interface SubAccount {
  id: number;
  username: string;
  email: string;
  role: string;
  status: string;
}

const subAccounts = ref<SubAccount[]>([]);
const total = ref(0);
const pageSize = ref(10);
const currentPage = ref(1);
const searchKeyword = ref('');

const dialogVisible = ref(false);
const isEditing = ref(false);
const form = reactive<Partial<SubAccount>>({});
const formRef = ref();
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
  role: [{ required: true, message: '请选择角色', trigger: 'change' }],
};

const fetchSubAccounts = () => {
  // TODO: 替换为实际的 API 调用
  // 示例数据
  subAccounts.value = [
    { id: 1, username: 'user1', email: 'user1@example.com', role: '管理员', status: '启用' },
    { id: 2, username: 'user2', email: 'user2@example.com', role: '编辑', status: '禁用' },
  ];
  total.value = subAccounts.value.length;
};

const handlePageChange = (page: number) => {
  currentPage.value = page;
  fetchSubAccounts();
};

const openAddDialog = () => {
  isEditing.value = false;
  Object.assign(form, { username: '', email: '', role: '', status: true });
  dialogVisible.value = true;
};

const openEditDialog = (account: SubAccount) => {
  isEditing.value = true;
  Object.assign(form, account);
  dialogVisible.value = true;
};

const submitForm = () => {
  formRef.value.validate((valid: boolean) => {
    if (valid) {
      if (isEditing.value) {
        // TODO: 调用更新子账号的 API
        ElMessage.success('子账号更新成功');
      } else {
        // TODO: 调用添加子账号的 API
        ElMessage.success('子账号添加成功');
      }
      dialogVisible.value = false;
      fetchSubAccounts();
    }
  });
};

const deleteSubAccount = (id: number) => {
  ElMessageBox.confirm('确定要删除该子账号吗？', '提示', {
    type: 'warning',
  })
    .then(() => {
      // TODO: 调用删除子账号的 API
      ElMessage.success('子账号删除成功');
      fetchSubAccounts();
    })
    .catch(() => {
      // 用户取消删除
    });
};

onMounted(() => {
  fetchSubAccounts();
});
</script>

<style scoped>
.mb-4 {
  margin-bottom: 16px;
}

.mt-4 {
  margin-top: 16px;
}
</style>
