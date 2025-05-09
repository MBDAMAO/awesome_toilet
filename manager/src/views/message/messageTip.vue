<template>
  <div class="flex flex-col w-full h-full" @click="dialogVisible = true">
    <div class="flex items-center w-full">
      <el-tag type="error" size="small">{{ item.type }}</el-tag>
      <div class="ml-2 flex items-center">{{ item.title }}</div>
      <div class="text-gray-400 ml-2 flex items-center">{{ item.time }}</div>
    </div>
    <div class="w-full">
      <span class="text-gray-400">{{ item.content }}</span>
    </div>
  </div>
  <el-dialog v-model="dialogVisible" :title="item.title" width="800" :modal="true">
    <span>{{ item.content }}</span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
        <el-button type="primary" @click="dialogVisible = false"> 确认 </el-button>
      </div>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue'
defineProps({
  item: {
    type: Object,
    default: () => {
      return {
        title: 'null',
        content: 'null',
        time: 'null',
        type: 'null',
      }
    },
  },
})
const loading = ref(true)
const hasContent = ref(false)

const dialogVisible = ref(false)

onMounted(() => {
  setTimeout(() => {
    loading.value = false
    hasContent.value = true
  }, 500)
})
</script>
<style scoped></style>
