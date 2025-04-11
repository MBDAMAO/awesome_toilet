<template>
  <Title :title="'厕所管理'" />

  <Container class="flex flex-col">
    <!-- 搜索栏 -->
    <div class="flex mb-4 gap-4">
      <el-input v-model="queryParams.name" placeholder="搜索厕所名称" clearable style="width: 200px" @clear="fetchToiletList"
        @keyup.enter="fetchToiletList" />
      <el-select v-model="queryParams.status" placeholder="选择状态" clearable style="width: 120px"
        @change="fetchToiletList">
        <el-option label="正常" value="1" />
        <el-option label="异常" value="2" />
        <el-option label="维护中" value="3" />
        <el-option label="关闭" value="4" />
      </el-select>
      <el-button type="primary" @click="fetchToiletList">搜索</el-button>
      <el-button type="success" @click="handleAdd">新增厕所</el-button>
    </div>

    <!-- 数据表格 -->
    <div class="overflow-auto w-full">
      <el-table :data="tableData" border style="width: 100%" max-height="500" v-loading="loading" stripe>
        <el-table-column prop="id" label="唯一ID" width="120" fixed />
        <el-table-column prop="name" label="名称" width="150" />
        <el-table-column prop="location" label="位置" width="180" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag :type="getStatusTagType(scope.row.status)" size="small">
              {{ statusNameMap[scope.row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="pits" label="坑位数量" width="100" align="center" />
        <el-table-column prop="devices" label="设备数量" width="100" align="center" />
        <el-table-column prop="description" label="备注" show-overflow-tooltip width="200" />
        <el-table-column prop="create_time" label="添加时间" width="180" />
        <el-table-column prop="update_time" label="修改时间" width="180" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="handleDetail(scope.row)">
              详情
            </el-button>
            <el-button link type="primary" size="small" @click="handleEdit(scope.row)">
              编辑
            </el-button>
            <el-button link type="danger" size="small" @click="handleDelete(scope.row.id)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <div class="w-full mt-4 flex justify-end">
      <el-pagination v-model:current-page="queryParams.page" v-model:page-size="queryParams.size"
        :page-sizes="[10, 20, 50, 100]" :background="true" layout="total, sizes, prev, pager, next, jumper"
        :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>
  </Container>

  <!-- 详情弹窗 -->
  <el-dialog v-model="detailDialogVisible" :title="'厕所详情 - ' + currentToilet?.name" width="50%" destroy-on-close>
    <ToiletDetail :toilet="currentToilet" v-if="detailDialogVisible" />
    <template #footer>
      <el-button @click="detailDialogVisible = false">关闭</el-button>
    </template>
  </el-dialog>

  <!-- 编辑弹窗 -->
  <el-dialog v-model="editDialogVisible" :title="currentToilet?.id ? '编辑厕所' : '新增厕所'" width="50%" destroy-on-close
    @closed="handleEditDialogClosed">
    <ToiletEditForm :toilet="currentToilet" @submit="handleEditSubmit" @cancel="editDialogVisible = false" />
  </el-dialog>

  <!-- 删除确认对话框 -->
  <el-dialog v-model="deleteDialogVisible" title="确认删除" width="30%">
    <span>确定要删除厕所 "{{ currentToilet?.name }}" 吗？此操作不可撤销。</span>
    <template #footer>
      <el-button @click="deleteDialogVisible = false">取消</el-button>
      <el-button type="danger" @click="confirmDelete" :loading="deleteLoading">
        确认删除
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getToiletList, deleteToilet, saveToilet } from '@/apis/toilet'
import type { ToiletItem } from '@/types'
import Container from '@/components/container/index.vue'
import Title from '@/components/title/index.vue'
import ToiletDetail from './components/ToiletDetail.vue'
import ToiletEditForm from './components/ToiletEditForm.vue'
import { ElMessage, ElMessageBox } from 'element-plus'

// 表格数据
const tableData = ref<ToiletItem[]>([])
const loading = ref(false)
const total = ref(0)

// 查询参数
const queryParams = reactive({
  page: 1,
  size: 10,
  name: '',
  status: ''
})

// 当前操作的厕所
const currentToilet = ref<ToiletItem | null>(null)

// 对话框控制
const detailDialogVisible = ref(false)
const editDialogVisible = ref(false)
const deleteDialogVisible = ref(false)
const deleteLoading = ref(false)

// 获取厕所列表
const fetchToiletList = async () => {
  try {
    loading.value = true
    const res = await getToiletList(queryParams)
    if (res.code === 0) {
      tableData.value = res.data.records || []
      total.value = res.data.total || 0
    }
  } catch (error) {
    console.error('获取厕所列表失败:', error)
    ElMessage.error('获取数据失败')
  } finally {
    loading.value = false
  }
}

// 分页处理
const handleSizeChange = (val: number) => {
  queryParams.size = val
  queryParams.page = 1
  fetchToiletList()
}

const handleCurrentChange = (val: number) => {
  queryParams.page = val
  fetchToiletList()
}

const statusNameMap = {
  '1': '正常',
  '2': '异常',
  '3': '维护中',
  '4': '关闭'
}

const getStatusTagType = (status: string) => {
  const statusMap: Record<string, string> = {
    '1': 'success',
    '2': 'danger',
    '3': 'warning',
    '4': 'info'
  }
  return statusMap[status] || ''
}

// 查看详情
const handleDetail = (row: ToiletItem) => {
  currentToilet.value = { ...row }
  detailDialogVisible.value = true
}

// 编辑厕所
const handleEdit = (row: ToiletItem) => {
  currentToilet.value = { ...row }
  editDialogVisible.value = true
}

// 新增厕所
const handleAdd = () => {
  currentToilet.value = {
    id: 0,
    name: '',
    location: '',
    status: 1,
    create_time: '',
    pits: 0,
    devices: 0,
    description: '',
    design_map: '',
    update_time: ''
  }
  editDialogVisible.value = true
}

// 编辑表单提交
const handleEditSubmit = async (formData: ToiletItem) => {
  try {
    // 这里调用API保存数据
    // await saveToilet(formData)
    console.log('保存数据:', formData)
    await saveToilet(formData)
    ElMessage.success(formData.id ? '修改成功' : '新增成功')
    editDialogVisible.value = false
    fetchToiletList() // 刷新列表
  } catch (error) {
    console.error('保存失败:', error)
    ElMessage.error('保存失败')
  }
}

// 编辑弹窗关闭
const handleEditDialogClosed = () => {
  currentToilet.value = null
}

// 删除厕所
const handleDelete = (id: number) => {
  currentToilet.value = tableData.value.find(item => item.id === id) || null
  deleteDialogVisible.value = true
}

// 确认删除
const confirmDelete = async () => {
  if (!currentToilet.value) return

  try {
    deleteLoading.value = true
    const resp = await deleteToilet(currentToilet.value.id)
    if (resp.code !== 0) {
      ElMessage.error(resp.message || '删除失败')
      deleteDialogVisible.value = false
      return;
    }
    ElMessage.success('删除成功')
    deleteDialogVisible.value = false
    fetchToiletList() // 刷新列表
  } catch (error) {
    console.error('删除失败:', error)
    ElMessage.error('删除失败')
  } finally {
    deleteLoading.value = false
  }
}

// 初始化加载数据
onMounted(() => {
  fetchToiletList()
})
</script>

<style scoped>
/* 可以添加自定义样式 */
</style>
