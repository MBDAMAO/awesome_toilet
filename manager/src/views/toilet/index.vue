<template>
    <Title :title="'厕所管理'"></Title>

    <Container class="flex flex-col">
        <div class="overflow-auto w-full">
            <el-table :data="tableData" border style="width: 100%" max-height="500">
                <el-table-column prop="id" label="唯一ID" width="180" />
                <el-table-column prop="name" label="名称" width="180" />
                <el-table-column prop="location" label="位置" width="180" show-overflow-tooltip />
                <el-table-column prop="status" label="状态" width="180">
                    <template #default="scope">
                        <div style="display: flex; align-items: center">
                            <el-tag type="success">{{ scope.row.status }}</el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="create_time" label="添加时间" width="180" />
                <el-table-column prop="pits" label="坑位数量" width="180" />
                <el-table-column prop="devices" label="设备数量" width="180" />
                <el-table-column prop="description" label="备注" show-overflow-tooltip width="180" />
                <el-table-column fixed="right" label="Operations" width="120">
                    <template #default>
                        <el-button link type="primary" size="small" @click="handleClick">
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
const handleClick = () => {
    console.log('click')
}
const tableData = [
    {
        id: '1',
        name: '厕所1',
        location: '1号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所1描述'
    },
    {
        id: '2',
        name: '厕所2',
        location: '2号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所2描述'
    },
    {
        id: '3',
        name: '厕所3',
        location: '3号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所3描述'
    }, {
        id: '1',
        name: '厕所1',
        location: '1号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所1描述'
    },
    {
        id: '2',
        name: '厕所2',
        location: '2号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所2描述'
    },
    {
        id: '3',
        name: '厕所3',
        location: '3号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所3描述'
    }, {
        id: '1',
        name: '厕所1',
        location: '1号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所1描述'
    },
    {
        id: '2',
        name: '厕所2',
        location: '2号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所2描述'
    },
    {
        id: '3',
        name: '厕所3',
        location: '3号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所3描述'
    }, {
        id: '1',
        name: '厕所1',
        location: '1号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所1描述'
    },
    {
        id: '2',
        name: '厕所2',
        location: '2号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所2描述'
    },
    {
        id: '3',
        name: '厕所3',
        location: '3号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所3描述'
    }, {
        id: '1',
        name: '厕所1',
        location: '1号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所1描述'
    },
    {
        id: '2',
        name: '厕所2',
        location: '2号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所2描述'
    },
    {
        id: '3',
        name: '厕所3',
        location: '3号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所3描述'
    }, {
        id: '1',
        name: '厕所1',
        location: '1号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所1描述'
    },
    {
        id: '2',
        name: '厕所2',
        location: '2号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所2描述'
    },
    {
        id: '3',
        name: '厕所3',
        location: '3号厕所',
        status: '正常',
        create_time: '2022-01-01 12:00:00',
        pits: 10,
        devices: 10,
        description: '厕所3描述'
    },
]
</script>
<style scoped></style>