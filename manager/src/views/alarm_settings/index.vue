<template>
    <div class="p-6 w-full max-w-6xl mx-auto space-y-6">
        <!-- 设置卡片 -->
        <el-card shadow="hover">
            <template #header>
                <div class="flex justify-between items-center">
                    <span class="text-lg font-semibold">阈值告警设置</span>
                </div>
            </template>

            <el-form :model="form" label-width="100px" label-position="left">
                <el-form-item label="厕所位置">
                    <el-select v-model="form.toiletId" placeholder="请选择厕所">
                        <el-option v-for="toilet in toilets" :key="toilet.id" :label="toilet.name" :value="toilet.id" />
                    </el-select>
                </el-form-item>

                <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <el-form-item v-for="item in form.thresholds" :key="item.key" :label="item.label">
                        <div class="flex items-center space-x-2 w-full">
                            <el-input-number v-model="item.min" :min="0" :max="item.max || 9999" placeholder="最小值"
                                class="flex-1" />
                            <span class="text-gray-400">~</span>
                            <el-input-number v-model="item.max" :min="item.min || 0" :max="9999" placeholder="最大值"
                                class="flex-1" />
                            <span class="text-sm text-gray-500">单位: {{ item.unit }}</span>
                        </div>
                    </el-form-item>
                </div>

                <el-form-item class="mt-6">
                    <el-button type="primary" @click="submit">{{ editingId ? '更新' : '保存设置' }}</el-button>
                    <el-button @click="reset">重置</el-button>
                </el-form-item>
            </el-form>
        </el-card>

        <!-- 阈值列表卡片 -->
        <el-card shadow="hover">
            <template #header>
                <div class="flex justify-between items-center">
                    <span class="text-lg font-semibold">告警阈值列表</span>
                </div>
            </template>

            <el-table :data="thresholdList" border stripe>
                <el-table-column prop="toiletName" label="厕所" />
                <el-table-column label="阈值项">
                    <template #default="{ row }">
                        <div class="space-y-1">
                            <div v-for="(val, key) in row.thresholds" :key="key">
                                {{ keyMap[key] || key }}：{{ val.min }} ~ {{ val.max }} {{ unitMap[key] }}
                            </div>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="160">
                    <template #default="{ row }">
                        <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
                        <el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const toilets = [
    { id: 1, name: '1号男厕' },
    { id: 2, name: '1号女厕' },
    { id: 3, name: '2号智能厕所' },
]

// 阈值表单
const form = reactive({
    toiletId: null,
    thresholds: [
        { key: 'temperature', label: '温度', min: null, max: null, unit: '℃' },
        { key: 'humidity', label: '湿度', min: null, max: null, unit: '%' },
        { key: 'electricity', label: '用电量', min: null, max: null, unit: 'kWh' },
        { key: 'water', label: '用水量', min: null, max: null, unit: 'L' },
        { key: 'paper', label: '纸巾余量', min: null, max: null, unit: '%' },
    ],
})

const thresholdList = ref<any[]>([]) // 模拟数据列表
const editingId = ref<number | null>(null)

// 显示用的单位 & 中文映射
const keyMap: Record<string, string> = {
    temperature: '温度',
    humidity: '湿度',
    electricity: '用电量',
    water: '用水量',
    paper: '纸巾余量',
}
const unitMap: Record<string, string> = {
    temperature: '℃',
    humidity: '%',
    electricity: 'kWh',
    water: 'L',
    paper: '%',
}

const submit = () => {
    if (!form.toiletId) return ElMessage.warning('请选择厕所位置')

    const toilet = toilets.find(t => t.id === form.toiletId)
    const data = {
        id: editingId.value || Date.now(), // 模拟 ID
        toiletId: form.toiletId,
        toiletName: toilet?.name,
        thresholds: form.thresholds.reduce((acc, item) => {
            acc[item.key] = { min: item.min, max: item.max }
            return acc
        }, {} as Record<string, { min: number | null; max: number | null }>),
    }

    if (editingId.value) {
        const index = thresholdList.value.findIndex(i => i.id === editingId.value)
        thresholdList.value[index] = data
        ElMessage.success('设置已更新')
    } else {
        thresholdList.value.push(data)
        ElMessage.success('设置已保存')
    }

    reset()
}

const reset = () => {
    form.toiletId = null
    form.thresholds.forEach(item => {
        item.min = null
        item.max = null
    })
    editingId.value = null
}

const handleEdit = (row: any) => {
    form.toiletId = row.toiletId
    form.thresholds.forEach(item => {
        const value = row.thresholds[item.key]
        if (value) {
            item.min = value.min
            item.max = value.max
        }
    })
    editingId.value = row.id
}

const handleDelete = (row: any) => {
    ElMessageBox.confirm('确定删除该阈值设置吗？', '提示', {
        type: 'warning',
    }).then(() => {
        thresholdList.value = thresholdList.value.filter(i => i.id !== row.id)
        ElMessage.success('已删除')
    })
}
</script>