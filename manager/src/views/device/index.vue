<template>
    <Title :title="'设备管理'"></Title>

    <Container class="flex flex-col">
        <div class="overflow-auto w-full">
            <el-table :data="tableData" border style="width: 100%" max-height="500">
                <el-table-column prop="id" label="设备ID" width="180" />
                <el-table-column prop="name" label="设备名称" width="180" />
                <el-table-column prop="toilet_name" label="所在厕所" width="180" />
                <el-table-column prop="device_type" label="设备类型" width="180" />

                <el-table-column prop="power" label="电量" width="180" />
                <el-table-column prop="status" label="状态" width="180">
                    <template #default="scope">
                        <div style="display: flex; align-items: center">
                            <el-tag type="success">{{ scope.row.status }}</el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="toilet_type" label="厕所类型" width="180" />
                <el-table-column prop="pit_type" label="厕位类型" width="180" />
                <el-table-column prop="description" label="备注" show-overflow-tooltip width="180" />
                <el-table-column fixed="right" label="Operations" width="120">
                    <template #default>
                        <el-button link type="primary" size="small" @click="">
                            Detail
                        </el-button>
                        <el-button link type="primary" size="small">Edit</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="w-full mt-2">
            <el-pagination v-model:current-page="currentPage1" :page-size="100" :size="size" :disabled="disabled"
                :background="background" layout="total, prev, pager, next, jumper" :total="1000"
                @size-change="handleSizeChange" @current-change="handleCurrentChange" />
        </div>
    </Container>

</template>
<script setup lang='ts'>
import Container from '@/components/container/index.vue'
import Title from '@/components/title/index.vue'
const tableData = [
    {
        id: 1,
        name: '设备A',
        toilet_name: '厕所1',
        device_type: '类型A',
        toilet_type: '类型X',
        pit_type: '类型Y',
        status: '正常',
        description: '这是设备A的备注'
    },
    {
        id: 2,
        name: '设备B',
        toilet_name: '厕所2',
        device_type: '类型B',
        toilet_type: '类型Y',
        pit_type: '类型Z',
        status: '正常',
        description: '这是设备B的备注'
    },
    // 可以继续添加更多的测试数据
];

</script>
<style scoped></style>