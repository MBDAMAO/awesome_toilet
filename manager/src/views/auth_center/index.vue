<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="min-h-screen bg-gray-100 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-md">
      <h2 class="mt-6 text-center text-3xl font-extrabold text-gray-900">
        统一认证中心
      </h2>
    </div>

    <div class="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
      <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
        <form class="space-y-6" @submit.prevent="handleLogin">
          <div>
            <label for="username" class="block text-sm font-medium text-gray-700">
              用户名
            </label>
            <div class="mt-1">
              <input id="username" v-model="loginForm.username" name="username" type="text" required
                class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm" />
            </div>
          </div>

          <div>
            <label for="password" class="block text-sm font-medium text-gray-700">
              密码
            </label>
            <div class="mt-1">
              <input id="password" v-model="loginForm.password" name="password" type="password" required
                class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm" />
            </div>
          </div>

          <div class="flex items-center justify-between">
            <div class="flex items-center">
              <input id="remember-me" v-model="loginForm.remember" name="remember-me" type="checkbox"
                class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded" />
              <label for="remember-me" class="ml-2 block text-sm text-gray-900">
                记住我
              </label>
            </div>


          </div>

          <div>
            <button type="submit"
              class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
              :disabled="loading">
              <span v-if="!loading">登录</span>
              <svg v-else class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg"
                fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor"
                  d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z">
                </path>
              </svg>
            </button>
          </div>
        </form>

        <div class="mt-6">
          <div class="relative">
            <div class="absolute inset-0 flex items-center">
              <div class="w-full border-t border-gray-300"></div>
            </div>
            <div class="relative flex justify-center text-sm">
              <span class="px-2 bg-white text-gray-500">
                其他登录方式 (暂无)
              </span>
            </div>
          </div>

        </div>
      </div>
    </div>

    <div class="mt-8 text-center text-sm text-gray-600">
      <p>© 2025 统一认证平台. 保留所有权利.</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { login, verifyToken } from '@/apis/user'
import { ElMessage } from 'element-plus'
import { onMounted, ref } from 'vue'
// import { useRouter } from 'vue-router'

// const router = useRouter()

const loading = ref(false)
const loginForm = ref({
  username: '',
  password: '',
  remember: false
})

const getQueryParam = (name: unknown) => {
  const query = window.location.search.substring(1)
  const vars = query.split('&')
  for (let i = 0; i < vars.length; i++) {
    const pair = vars[i].split('=')
    if (decodeURIComponent(pair[0]) === name) {
      return decodeURIComponent(pair[1])
    }
  }
  return null
}

const handleLogin = async () => {
  try {
    loading.value = true
    const response = await login(loginForm.value.username, loginForm.value.password)
    if (response.code === 0) {
      ElMessage({
        message: '登录成功',
        type: 'success',
        duration: 3000
      })
      localStorage.setItem('token', response.data.token);
      const redirectUri = getQueryParam('redirect_uri') || window.location.origin
      window.location.href = `${redirectUri}?token=${response.data.token}`
    } else {
      ElMessage({
        message: response.message,
        type: 'error',
        duration: 3000
      })
    }
  } catch (error) {
    ElMessage({
      message: '登录失败',
      type: 'error',
      duration: 3000
    })
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  const token = localStorage.getItem('token')
  if (token) {
    verifyToken(token).then((verify) => {
      if (verify) {
        const redirectUri = getQueryParam('redirect_uri') || window.location.origin
        window.location.href = `${redirectUri}?token=${token}`
      }
    })
  }
})
</script>

<style scoped>
/* 可以添加自定义样式 */
</style>
