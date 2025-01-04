<template>
    <div class="w-full h-full flex flex-col">
        <Title :title="'消息列表'"> <el-checkbox v-model="checked">仅显示未读</el-checkbox></Title>

        <Container class="flex items-center justify-center flex-col h-[90%]" v-loading="loading">
            <el-empty description="无内容" v-if="!hasContent" />
            <div class="max-h-[100%] w-full overflow-auto flex flex-col p-2" v-if="hasContent">
                <Container class="mt-2 mb-2 cursor-pointer" v-for="item in messages">
                    <MessageTip :item="item" />
                </Container>
            </div>
            <div class="w-full mt-2">
                <el-pagination v-model:current-page="currentPage1" :page-size="100" :size="size" :disabled="disabled"
                    :background="background" layout="total, prev, pager, next, jumper" :total="1000"
                    @size-change="handleSizeChange" @current-change="handleCurrentChange" />
            </div>
        </Container>
    </div>
</template>
<script setup lang='ts'>
import Container from '@/components/container/index.vue'
import Title from '@/components/title/index.vue'
import { onMounted, ref } from 'vue'
import type { ComponentSize } from 'element-plus'
import MessageTip from './messageTip.vue'
const currentPage1 = ref(5)
const size = ref<ComponentSize>('default')
const background = ref(false)
const disabled = ref(false)
const loading = ref(true)
const hasContent = ref(false)
const checked = ref(false)

const messages = [
    {
        title: '消息标题',
        content: '21',
        time: '2021-12-12 12:12:12',
        type: 'success'
    },
    {
        title: '消息标题',
        content: '消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容',
        time: '2021-12-12 12:12:12',
        type: 'success'
    },
    {
        title: '消息标题',
        content: '消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容',
        time: '2021-12-12 12:12:12',
        type: 'success'
    },
    {
        title: '消息标题',
        content: '消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容',
        time: '2021-12-12 12:12:12',
        type: 'success'
    },
    {
        title: '消息标题',
        content: '消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容',
        time: '2021-12-12 12:12:12',
        type: 'success'
    },
    {
        title: '消息标题',
        content: '消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容',
        time: '2021-12-12 12:12:12',
        type: 'success'
    },
    {
        title: '消息标题',
        content: '消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容',
        time: '2021-12-12 12:12:12',
        type: 'success'
    },
    {
        title: '消息标题',
        content: '消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容',
        time: '2021-12-12 12:12:12',
        type: 'success'
    },
    {
        title: '消息标题',
        content: '消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容',
        time: '2021-12-12 12:12:12',
        type: 'success'
    },
    {
        title: '消息标题',
        content: '消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容',
        time: '2021-12-12 12:12:12',
        type: 'success'
    },
]
onMounted(() => {
    setTimeout(() => {
        loading.value = false
        hasContent.value = true
    }, 500)
})
const handleSizeChange = (val: number) => {
    console.log(`${val} items per page`)
}
const handleCurrentChange = (val: number) => {
    console.log(`current page: ${val}`)
    loading.value = true
    setTimeout(() => {
        loading.value = false
        hasContent.value = true
    }, 500)
}
</script>
<style scoped>
::-webkit-scrollbar {
    display: none;
}
</style>