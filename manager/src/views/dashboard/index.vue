<template>
  <div class="w-full">
    <div v-if="loading" class="flex justify-center items-center h-[calc(100vh-200px)]">
      <el-icon class="is-loading" color="#409EFC" :size="50">
        <Loading />
      </el-icon>
    </div>
    <div v-else class="p-6 max-w-7xl mx-auto space-y-6">
      <div class="flex gap-6 h-60">
        <!-- 用纸量卡片 -->
        <div class="flex-1 card rounded-xl shadow-sm p-6 border-l-4 border-emerald-500 flex flex-col">
          <div class="text-sm font-medium  mb-4">今日用纸量</div>
          <div class="text-7xl font-light  mb-2">3,222<span class="text-3xl ml-2 ">卷</span></div>
          <div class="text-sm  mt-auto">较昨日 <span class="text-emerald-500">↑12%</span></div>
        </div>

        <!-- 账号信息卡片 -->
        <div class="w-96 card rounded-xl shadow-sm p-6 flex flex-col">
          <div class="text-sm font-medium mb-4">账号信息</div>
          <div class="space-y-4">
            <div class="flex justify-between  border-b border-gray-100">
              <span class="">管理员：</span>
              <span class=" font-medium">{{ username }}</span>
            </div>
            <div class="flex justify-between  border-b border-gray-100">
              <span class="">最后登录：</span>
              <span class="font-medium">{{ lastLogin }}</span>
            </div>
            <div class="flex justify-between ">
              <span class="">登录位置</span>
              <span class=" font-medium">{{ loginLocation }}</span>
            </div>
            <div class="flex justify-between ">
              <span class="">登录平台</span>
              <span class=" font-medium">{{ loginDevice }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 第二行：水电用量 -->
      <div class="flex gap-6 h-60">
        <!-- 用水量卡片 -->
        <div class="flex-1 card rounded-xl shadow-sm p-6 border-l-4 border-blue-500 flex flex-col">
          <div class="text-sm font-medium  mb-4">今日用水量</div>
          <div class="text-7xl font-light  mb-2">1,842<span class="text-3xl ml-2 ">m³</span></div>
          <div class="text-sm  mt-auto">周同比 <span class="text-red-500">↓5%</span></div>
        </div>

        <!-- 用电量卡片 -->
        <div class="flex-1 card rounded-xl shadow-sm p-6 border-l-4 border-amber-400 flex flex-col">
          <div class="text-sm font-medium  mb-4">今日用电量</div>
          <div class="text-7xl font-light  mb-2">2,156<span class="text-3xl ml-2 ">kWh</span></div>
          <div class="text-sm  mt-auto">月平均 <span class="text-amber-500">→2,100</span></div>
        </div>
      </div>

      <!-- 第三行：客流量 -->
      <div class="flex gap-6 h-60">
        <!-- 客流量卡片 -->
        <div class="flex-1 card rounded-xl shadow-sm p-6 border-l-4 border-purple-500 flex flex-col">
          <div class="text-sm font-medium  mb-4">今日客流量</div>
          <div class="text-7xl font-light  mb-2">1,028<span class="text-3xl ml-2 ">人</span></div>
          <div class="flex gap-8 mt-auto">
            <div class="flex items-center">
              <span class=" mr-2">男</span>
              <span class="text-xl font-medium">642</span>
            </div>
            <div class="flex items-center">
              <span class=" mr-2">女</span>
              <span class="text-xl font-medium">386</span>
            </div>
          </div>
        </div>

        <!-- 系统状态卡片 -->
        <div class="w-96 card rounded-xl shadow-sm p-6 flex flex-col items-center justify-center">
          <div class="text-sm font-medium  mb-4">系统状态</div>
          <div class="w-16 h-16 rounded-full mb-3 bg-gradient-to-r from-emerald-400 to-emerald-600"></div>
          <div class="text-lg font-medium  mb-1">所有设备在线</div>
          <div class="text-sm ">最后更新: 10:15:23</div>
        </div>
      </div>

      <!-- 第四行：环境监测 -->
      <div class="card rounded-xl shadow-sm p-6">
        <div class="text-sm font-medium  mb-4">环境质量监测</div>
        <div class="grid grid-cols-4 gap-4">
          <div class="flex flex-col items-center p-4 rounded-lg ">
            <span class=" text-sm mb-1">温度</span>
            <span class="text-xl font-medium">24.5℃</span>
          </div>
          <div class="flex flex-col items-center p-4 rounded-lg ">
            <span class=" text-sm mb-1">湿度</span>
            <span class="text-xl font-medium">58%</span>
          </div>
          <div class="flex flex-col items-center p-4 rounded-lg ">
            <span class=" text-sm mb-1">空气质量</span>
            <span class="text-xl font-medium text-emerald-500">优良</span>
          </div>
          <div class="flex flex-col items-center p-4 rounded-lg ">
            <span class=" text-sm mb-1">PM2.5</span>
            <span class="text-xl font-medium">32</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { getHomeStat } from '@/apis/stat';
import { onMounted, ref } from 'vue';
import { Loading } from '@element-plus/icons-vue';
const username = ref("")
const lastLogin = ref("")
const loginLocation = ref("")
const loginDevice = ref("")

const loading = ref(true) // 新增加载状态

onMounted(() => {
  getHomeStat().then(res => {
    username.value = res.data.user.name
    lastLogin.value = res.data.login_history[0].time
    loginLocation.value = res.data.login_history[0].location
    loginDevice.value = res.data.login_history[0].platform
  }).finally(() => {
    loading.value = false // 数据加载完成后关闭加载状态
  });
})
</script>

<style scoped>
/* 卡片悬停效果 */
.card {
  transition: all 0.2s ease;
}

.card:hover {
  @apply shadow-md transform scale-[1.01];
}

/* 响应式调整 */
@media (max-width: 1024px) {
  .flex-col-on-mobile {
    @apply flex-col;
  }

  .w-full-on-mobile {
    @apply w-full;
  }
}

/* 原有样式保持不变... */

/* 加载动画样式 */
.is-loading {
  animation: rotating 2s linear infinite;
}

@keyframes rotating {
  from {
    transform: rotate(0deg);
  }

  to {
    transform: rotate(360deg);
  }
}
</style>
