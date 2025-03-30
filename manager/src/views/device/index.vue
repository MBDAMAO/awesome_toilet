<template>
    <Title :title="'设备管理'"></Title>

    <Container class="flex flex-col">
        <div class="overflow-auto w-full">
            <el-table :data="tableData" border style="width: 100%" max-height="500" v-loading="loading">
                <el-table-column prop="id" label="设备ID" width="180" />
                <el-table-column prop="name" label="设备名称" width="180" />
                <el-table-column prop="toilet_name" label="所在厕所" width="180" />
                <el-table-column prop="device_type" label="设备类型" width="180" />
                <el-table-column prop="power" label="电量" width="180" />
                <el-table-column prop="status" label="状态" width="180">
                    <template #default="scope">
                        <div style="display: flex; align-items: center">
                            <el-tag :type="getStatusTagType(scope.row.status)">
                                {{ scope.row.status }}
                            </el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="toilet_type" label="厕所类型" width="180" />
                <el-table-column prop="pit_type" label="厕位类型" width="180" />
                <el-table-column prop="description" label="备注" show-overflow-tooltip width="180" />
                <el-table-column fixed="right" label="操作" width="180">
                    <template #default="scope">
                        <el-button link type="primary" size="small" @click="handleDetail(scope.row)">
                            详情
                        </el-button>
                        <el-button link type="primary" size="small" @click="handleEdit(scope.row)">
                            编辑
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="w-full mt-2">
            <el-pagination v-model:current-page="queryParams.page" v-model:page-size="queryParams.size"
                :page-sizes="[10, 20, 50, 100]" :background="true" layout="total, sizes, prev, pager, next, jumper"
                :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
        </div>
    </Container>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getDeviceList } from '@/apis/device'
import Container from '@/components/container/index.vue'
import Title from '@/components/title/index.vue'
import type { DeviceItem } from '@/types'

// 表格数据
const tableData = ref<DeviceItem[]>([])
// 加载状态
const loading = ref(false)
// 总条数
const total = ref(0)

// 查询参数
const queryParams = reactive({
    page: 1,
    size: 10,
    // 可以添加其他查询参数
    name: '',
    status: ''
})

// 获取设备列表
const fetchDeviceList = async () => {
    try {
        loading.value = true
        const res = await getDeviceList(queryParams)
        if (res.code === 0) {
            tableData.value = res.data.records || []
            total.value = res.data.total || 0
        }
    } catch (error) {
        console.error('获取设备列表失败:', error)
    } finally {
        loading.value = false
    }
}

// 分页大小改变
const handleSizeChange = (val: number) => {
    queryParams.size = val
    queryParams.page = 1 // 重置到第一页
    fetchDeviceList()
}

// 当前页改变
const handleCurrentChange = (val: number) => {
    queryParams.page = val
    fetchDeviceList()
}

// 状态标签类型
const getStatusTagType = (status: string) => {
    const statusMap: Record<string, string> = {
        '正常': 'success',
        '异常': 'danger',
        '离线': 'warning',
        '维护中': 'info'
    }
    return statusMap[status] || ''
}

// 查看详情
const handleDetail = (row: DeviceItem) => {
    console.log('查看详情:', row)
    // 这里可以跳转到详情页或打开详情弹窗
}

// 编辑设备
const handleEdit = (row: DeviceItem) => {
    console.log('编辑设备:', row)
    // 这里可以跳转到编辑页或打开编辑弹窗
}

// 初始化加载数据
onMounted(() => {
    fetchDeviceList()
})
</script>

<style scoped>
/* 可以添加自定义样式 */
</style>