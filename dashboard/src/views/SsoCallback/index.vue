<template>
  <div class="h-full w-full flex items-center justify-center">
    <div class="text-center">
      <el-icon class="is-loading" size="50">
        <Loading />
      </el-icon>
      <p class="mt-4">正在处理登录信息，请稍候...</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Loading } from '@element-plus/icons-vue'
import { handleSSOCallback } from '@/apis/auth'

const route = useRoute()
const router = useRouter()

onMounted(async () => {
  try {
    // 处理SSO回调，通常是从URL参数中获取token
    console.log(route.query)
    const token = route.query.token as string
    await handleSSOCallback(token)

    // 登录成功后跳转到首页或原始请求页面
    const redirect = route.query.redirect as string || '/'
    router.push(redirect)
  } catch (error) {
    ElMessage.error('登录失败: ' + (error as Error).message)
    router.push('/login')
  }
})
</script>
