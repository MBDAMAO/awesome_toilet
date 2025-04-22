<template>
  <div class="w-full h-full flex flex-col">
    <Title :title="'消息列表'"> <el-checkbox v-model="checked">仅显示未读</el-checkbox></Title>

    <Container class="flex items-center justify-center flex-col" v-loading="loading">
      <el-empty description="无内容" v-if="!hasContent" />
      <div class="max-h-[100%] w-full overflow-auto flex flex-col p-2" v-if="hasContent">
        <div class="mt-4 mb-4 cursor-pointer" v-for="item in messages">
          <MessageTip :item="item" :key="item.id" />
        </div>
      </div>
      <div class="w-full mt-2">
        <el-pagination v-model:current-page="currentPage1" :page-size="10" :total="total" :size="size"
          :disabled="disabled" :background="background" layout="total, prev, pager, next, jumper"
          @size-change="handleSizeChange" @current-change="handleCurrentChange" />
      </div>
    </Container>
  </div>
</template>
<script setup lang='ts'>
import Container from '@/components/container/index.vue'
import Title from '@/components/title/index.vue'
import { onMounted, ref, watch } from 'vue'
import type { ComponentSize } from 'element-plus'
import MessageTip from './messageTip.vue'
import { getMessageList } from '@/apis/msg'

const currentPage1 = ref(1)
const size = ref<ComponentSize>('default')
const background = ref(false)
const disabled = ref(false)
const loading = ref(true)
const hasContent = ref(false)
const checked = ref(false) // 是否仅显示未读
const total = ref(0)
const messages = ref<any[]>([])

const fetchMessages = async () => {
  loading.value = true
  try {
    const res = await getMessageList({
      page: currentPage1.value,
      size: 10,
      is_read: checked.value ? false : undefined
    })
    if (res.code === 0) {
      messages.value = res.data.records.map((item: any) => ({
        title: `消息 ${item.id}`,
        content: item.message,
        time: item.create_time,
        type: item.is_read ? 'info' : 'success'
      }))
      total.value = res.data.total
      hasContent.value = messages.value.length > 0
    } else {
      messages.value = []
      hasContent.value = false
    }
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchMessages()
})

watch([currentPage1, checked], () => {
  fetchMessages()
})

const handleSizeChange = (val: number) => {
  console.log(`${val} items per page`)
  // 你可以在这里更新 size，如果后端支持
}
const handleCurrentChange = (val: number) => {
  console.log(`current page: ${val}`)
  currentPage1.value = val
}

</script>
<style scoped>
::-webkit-scrollbar {
  display: none;
}
</style>
