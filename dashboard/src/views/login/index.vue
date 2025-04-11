<template>
  <div
    class="h-screen w-screen bg-gradient-to-br from-slate-700 via-slate-800 to-slate-900 flex items-center justify-center">
    <div class="w-[500px] bg-white/10 backdrop-blur-lg rounded-2xl shadow-2xl p-10 flex flex-col items-center">
      <h2 class="text-3xl font-semibold text-white mb-10 tracking-wide">系统登录</h2>
      <el-button type="primary" @click="redirectToSSO"
        class="w-full h-12 text-lg font-medium rounded-lg transition-all duration-300 hover:shadow-xl">
        使用统一认证登录
      </el-button>
    </div>
  </div>
</template>

<script setup lang='ts'>
import { computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const isDevelopment = computed(() => import.meta.env.MODE === 'development')

const ssoLoginUrl = computed(() => {
  const baseSsoUrl = import.meta.env.VITE_SSO_URL || 'http://127.0.0.1:18014/page/auth'
  const redirectUri = encodeURIComponent(window.location.origin + '/sso-callback')
  return `${baseSsoUrl}?redirect_uri=${redirectUri}`
})

function redirectToSSO() {
  window.location.href = ssoLoginUrl.value
}
</script>

<style scoped>
h2 {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

.el-button {
  font-family: 'Inter', sans-serif;
}
</style>
