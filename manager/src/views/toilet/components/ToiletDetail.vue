<template>
    <div class="toilet-detail">
        <el-descriptions :column="2" border>
            <el-descriptions-item label="厕所ID">{{ toilet.id }}</el-descriptions-item>
            <el-descriptions-item label="厕所名称">{{ toilet.name }}</el-descriptions-item>
            <el-descriptions-item label="位置">{{ toilet.location }}</el-descriptions-item>
            <el-descriptions-item label="状态">
                <el-tag :type="getStatusTagType(toilet.status)">
                    {{ toilet.status }}
                </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="坑位数量">{{ toilet.pits }}</el-descriptions-item>
            <el-descriptions-item label="设备数量">{{ toilet.devices }}</el-descriptions-item>
            <el-descriptions-item label="添加时间">{{ toilet.create_time }}</el-descriptions-item>
            <el-descriptions-item label="备注" :span="2">
                {{ toilet.description || '无' }}
            </el-descriptions-item>
        </el-descriptions>

        <div class="mt-6">
            <h3 class="text-lg font-medium mb-3">设备列表</h3>
            <el-table :data="devices" border style="width: 100%">
                <el-table-column prop="id" label="设备ID" width="120" />
                <el-table-column prop="name" label="设备名称" width="150" />
                <el-table-column prop="type" label="设备类型" width="120" />
                <el-table-column prop="status" label="状态" width="100">
                    <template #default="scope">
                        <el-tag :type="getDeviceStatusTagType(scope.row.status)" size="small">
                            {{ scope.row.status }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="last_check" label="最后检查" width="180" />
            </el-table>
        </div>
    </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import type { ToiletItem } from '@/types'

const props = defineProps<{
    toilet: ToiletItem
}>()

// 模拟设备数据
const devices = computed(() => {
    return [
        { id: 1, name: '设备A', type: '传感器', status: '正常', last_check: '2023-05-10 14:30' },
        { id: 2, name: '设备B', type: '控制器', status: '正常', last_check: '2023-05-10 14:35' },
        { id: 3, name: '设备C', type: '显示器', status: '异常', last_check: '2023-05-09 10:20' }
    ]
})

const getStatusTagType = (status: string) => {
    const statusMap: Record<string, string> = {
        '正常': 'success',
        '异常': 'danger',
        '维护中': 'warning',
        '关闭': 'info'
    }
    return statusMap[status] || ''
}

const getDeviceStatusTagType = (status: string) => {
    const statusMap: Record<string, string> = {
        '正常': 'success',
        '异常': 'danger',
        '离线': 'warning',
        '维护中': 'info'
    }
    return statusMap[status] || ''
}
</script>

<style scoped>
.toilet-detail {
    padding: 10px;
}
</style>