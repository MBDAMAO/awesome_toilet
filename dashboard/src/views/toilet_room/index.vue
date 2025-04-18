<template>
  <div class="h-screen bg-gray-50 dark:bg-gray-900 p-4 overflow-auto">
    <!-- 头部厕所名称和返回按钮 -->
    <div class="flex items-center justify-between mb-6">
      <button @click="$router.back()"
        class="flex items-center text-blue-500 hover:text-blue-700 dark:text-blue-400 dark:hover:text-blue-300">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-1" viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd"
            d="M9.707 16.707a1 1 0 01-1.414 0l-6-6a1 1 0 010-1.414l6-6a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l4.293 4.293a1 1 0 010 1.414z"
            clip-rule="evenodd" />
        </svg>
        返回
      </button>
      <h1 class="text-2xl font-bold text-gray-800 dark:text-white text-center flex-1">
        {{ toilet.name }}
        <span class="ml-2 text-sm px-2 py-1 rounded-full" :class="statusClass">
          {{ toiletStatus }}
        </span>
      </h1>
      <div class="w-10"></div> <!-- 占位保持对称 -->
    </div>

    <div class="flex flex-col lg:flex-row gap-6">
      <!-- 左侧数据面板 -->
      <div class="w-full lg:w-1/4 flex flex-col gap-6">
        <!-- 侧位占用统计 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-4">
          <h2 class="text-lg font-semibold text-gray-700 dark:text-gray-200 mb-4 flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-2 text-blue-500" fill="none" viewBox="0 0 24 24"
              stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
            </svg>
            侧位占用统计
          </h2>

          <div class="grid grid-cols-2 gap-4 mb-4">
            <div class="text-center">
              <div class="text-3xl font-bold text-blue-500">{{ toilet.male.occupied }}/{{
                toilet.male.total }}</div>
              <div class="text-sm text-gray-500 dark:text-gray-400">男厕占用</div>
              <div class="h-2 bg-gray-200 dark:bg-gray-700 rounded-full mt-2 overflow-hidden">
                <div class="h-full bg-blue-500 rounded-full"
                  :style="{ width: `${(toilet.male.occupied / toilet.male.total) * 100}%` }"></div>
              </div>
            </div>
            <div class="text-center">
              <div class="text-3xl font-bold text-pink-500">{{ toilet.female.occupied }}/{{
                toilet.female.total }}</div>
              <div class="text-sm text-gray-500 dark:text-gray-400">女厕占用</div>
              <div class="h-2 bg-gray-200 dark:bg-gray-700 rounded-full mt-2 overflow-hidden">
                <div class="h-full bg-pink-500 rounded-full"
                  :style="{ width: `${(toilet.female.occupied / toilet.female.total) * 100}%` }">
                </div>
              </div>
            </div>
          </div>

          <div class="border-t border-gray-200 dark:border-gray-700 pt-3">
            <h3 class="text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">客流数据</h3>
            <div class="flex justify-between text-sm">
              <div class="text-center">
                <div class="text-xl font-bold">{{ toilet.todayVisits }}</div>
                <div class="text-gray-500 dark:text-gray-400">今日</div>
              </div>
              <div class="text-center">
                <div class="text-xl font-bold">{{ toilet.avgStayTime }}m</div>
                <div class="text-gray-500 dark:text-gray-400">平均用时</div>
              </div>
              <div class="text-center">
                <div class="text-xl font-bold">{{ toilet.peakHour }}</div>
                <div class="text-gray-500 dark:text-gray-400">高峰时段</div>
              </div>
            </div>
          </div>
        </div>

        <!-- 环境信息指标 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-4 flex-1">
          <h2 class="text-lg font-semibold text-gray-700 dark:text-gray-200 mb-4 flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-2 text-green-500" fill="none" viewBox="0 0 24 24"
              stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6" />
            </svg>
            环境监测
          </h2>

          <div class="space-y-4">
            <EnvironmentIndicator title="空气质量" value="85" unit="AQI" level="good" icon="🌬️" />
            <EnvironmentIndicator title="温度" value="26.5" unit="°C" level="normal" icon="🌡️" />
            <EnvironmentIndicator title="湿度" value="65" unit="%" level="normal" icon="💧" />
            <EnvironmentIndicator title="异味指数" value="32" unit="" level="good" icon="👃" />
            <EnvironmentIndicator title="噪音" value="58" unit="dB" level="normal" icon="🔊" />
          </div>
        </div>
      </div>

      <!-- 中间主体区域 -->
      <div class="flex-1 flex flex-col gap-6">
        <!-- 厕所户型导览图 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-4">
          <h2 class="text-lg font-semibold text-gray-700 dark:text-gray-200 mb-4">厕所导览图</h2>

          <div class="relative bg-gray-100 dark:bg-gray-700 rounded-lg overflow-hidden">
            <!-- 这里放置实际的厕所平面图，可以使用SVG或图片 -->
            <svg viewBox="0 0 800 400" class="w-full h-auto">
              <!-- 背景 -->
              <rect x="0" y="0" width="800" height="400" fill="#f0f0f0" class="dark:fill-gray-600" />

              <!-- 走廊 -->
              <rect x="300" y="50" width="200" height="300" fill="#ddd" class="dark:fill-gray-500" />

              <!-- 男厕区域 -->
              <rect x="50" y="50" width="250" height="300" fill="#e3f2fd" class="dark:fill-blue-900" />
              <text x="175" y="80" font-size="20" text-anchor="middle" fill="#1976d2"
                class="dark:fill-blue-300">男厕</text>

              <!-- 女厕区域 -->
              <rect x="500" y="50" width="250" height="300" fill="#fce4ec" class="dark:fill-pink-900" />
              <text x="625" y="80" font-size="20" text-anchor="middle" fill="#d81b60"
                class="dark:fill-pink-300">女厕</text>

              <!-- 男厕隔间 -->
              <template v-for="(stall, index) in maleStalls" :key="'male'+index">
                <rect :x="stall.x" :y="stall.y" width="40" height="60" rx="5"
                  :fill="stall.occupied ? '#ffcdd2' : '#c8e6c9'" class="dark:fill-gray-400" />
                <text :x="stall.x + 20" :y="stall.y + 35" font-size="14" text-anchor="middle" fill="#000"
                  class="dark:fill-white">
                  {{ index + 1 }}
                </text>
                <circle :cx="stall.x + 30" :cy="stall.y + 10" r="5" :fill="stall.occupied ? '#f44336' : '#4caf50'" />
              </template>

              <!-- 女厕隔间 -->
              <template v-for="(stall, index) in femaleStalls" :key="'female'+index">
                <rect :x="stall.x" :y="stall.y" width="40" height="60" rx="5"
                  :fill="stall.occupied ? '#ffcdd2' : '#c8e6c9'" class="dark:fill-gray-400" />
                <text :x="stall.x + 20" :y="stall.y + 35" font-size="14" text-anchor="middle" fill="#000"
                  class="dark:fill-white">
                  {{ index + 1 }}
                </text>
                <circle :cx="stall.x + 30" :cy="stall.y + 10" r="5" :fill="stall.occupied ? '#f44336' : '#4caf50'" />
              </template>

              <!-- 洗手台 -->
              <rect x="350" y="100" width="100" height="20" fill="#bbdefb" rx="3" class="dark:fill-blue-800" />
              <text x="400" y="115" font-size="12" text-anchor="middle" fill="#0d47a1"
                class="dark:fill-blue-200">洗手台</text>

              <!-- 入口 -->
              <rect x="375" y="20" width="50" height="30" fill="#81c784" rx="3" />
              <text x="400" y="40" font-size="12" text-anchor="middle" fill="#1b5e20">入口</text>

              <!-- 无障碍厕所 -->
              <rect x="350" y="250" width="60" height="80" fill="#fff9c4" rx="5" class="dark:fill-yellow-800" />
              <text x="380" y="290" font-size="12" text-anchor="middle" fill="#f57f17"
                class="dark:fill-yellow-200">无障碍</text>
            </svg>

            <!-- 图例 -->
            <div class="absolute bottom-4 left-4 bg-white dark:bg-gray-700 p-2 rounded-lg shadow-md text-sm">
              <div class="flex items-center mb-1">
                <div class="w-4 h-4 rounded-full bg-green-500 mr-2"></div>
                <span class="text-gray-700 dark:text-gray-300">空闲</span>
              </div>
              <div class="flex items-center mb-1">
                <div class="w-4 h-4 rounded-full bg-red-500 mr-2"></div>
                <span class="text-gray-700 dark:text-gray-300">使用中</span>
              </div>
              <div class="flex items-center">
                <div class="w-4 h-4 rounded-full bg-yellow-500 mr-2"></div>
                <span class="text-gray-700 dark:text-gray-300">无障碍设施</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 底部区域 -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
          <!-- 满意度评价 -->
          <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-4">
            <h2 class="text-lg font-semibold text-gray-700 dark:text-gray-200 mb-4 flex items-center">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-2 text-yellow-500" fill="none"
                viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M11.049 2.927c.3-.921 1.603-.921 1.902 0l1.519 4.674a1 1 0 00.95.69h4.915c.969 0 1.371 1.24.588 1.81l-3.976 2.888a1 1 0 00-.363 1.118l1.518 4.674c.3.922-.755 1.688-1.538 1.118l-3.976-2.888a1 1 0 00-1.176 0l-3.976 2.888c-.783.57-1.838-.197-1.538-1.118l1.518-4.674a1 1 0 00-.363-1.118l-3.976-2.888c-.784-.57-.38-1.81.588-1.81h4.914a1 1 0 00.951-.69l1.519-4.674z" />
              </svg>
              满意度评价
            </h2>

            <div class="flex items-center justify-between mb-3">
              <div class="text-3xl font-bold text-gray-800 dark:text-white">
                {{ toilet.rating }}<span class="text-xl text-gray-500">/5</span>
              </div>
              <div class="flex">
                <svg v-for="i in 5" :key="i" xmlns="http://www.w3.org/2000/svg" class="h-6 w-6"
                  :class="i <= Math.floor(toilet.rating) ? 'text-yellow-400' : 'text-gray-300 dark:text-gray-500'"
                  viewBox="0 0 20 20" fill="currentColor">
                  <path
                    d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                </svg>
              </div>
            </div>

            <div class="space-y-2">
              <div v-for="(comment, index) in toilet.comments.slice(0, 2)" :key="index"
                class="border-b border-gray-200 dark:border-gray-700 pb-2">
                <div class="flex items-center justify-between">
                  <span class="font-medium text-gray-700 dark:text-gray-300">{{ comment.user }}</span>
                  <span class="text-sm text-gray-500">{{ comment.time }}</span>
                </div>
                <p class="text-gray-600 dark:text-gray-400 text-sm mt-1">{{ comment.content }}</p>
              </div>
            </div>

            <button
              class="mt-3 w-full py-2 bg-blue-50 dark:bg-gray-700 text-blue-500 dark:text-blue-400 rounded-lg hover:bg-blue-100 dark:hover:bg-gray-600 transition-colors text-sm">
              查看全部评价 ({{ toilet.comments.length }})
            </button>
          </div>

          <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md overflow-hidden">
            <div class="relative pt-[56.25%] bg-black">

              <video src="@/assets/test.mp4" alt="广告" autoplay loop
                class="absolute inset-0 w-full h-full object-cover opacity-70" />

              <div class="absolute bottom-2 left-2 bg-yellow-500 text-white text-xs px-2 py-1 rounded">
                广告
              </div>
            </div>
            <div class="p-3">
              <h3 class="font-medium text-gray-700 dark:text-gray-300">城市文明如厕宣传</h3>
              <p class="text-sm text-gray-500 dark:text-gray-400 mt-1">倡导文明如厕，共建卫生城市</p>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧数据面板 -->
      <div class="w-full lg:w-1/4 flex flex-col gap-6">
        <!-- 警报信息 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-4">
          <h2 class="text-lg font-semibold text-gray-700 dark:text-gray-200 mb-4 flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-2 text-red-500" fill="none" viewBox="0 0 24 24"
              stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
            </svg>
            警报信息
          </h2>

          <div class="space-y-3">
            <AlertItem v-for="alert in toilet.alerts" :key="alert.id" :type="alert.type" :message="alert.message"
              :time="alert.time" :level="alert.level" />
          </div>
        </div>

        <!-- 环境监测图表 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-4 flex-1">
          <h2 class="text-lg font-semibold text-gray-700 dark:text-gray-200 mb-4 flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-2 text-purple-500" fill="none" viewBox="0 0 24 24"
              stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
            </svg>
            环境趋势
          </h2>

          <div class="h-40">
            <!-- 这里可以放置环境数据的图表 -->
            <div class="flex items-end h-32 space-x-1">
              <div v-for="(item, index) in envChartData" :key="index" class="flex-1 flex flex-col items-center">
                <div class="w-full bg-blue-200 dark:bg-blue-800 rounded-t" :style="{ height: `${item.value}%` }">
                </div>
                <span class="text-xs text-gray-500 mt-1">{{ item.time }}</span>
              </div>
            </div>

            <div class="flex justify-between mt-4 text-xs text-gray-500">
              <span>异味指数</span>
              <span>噪音分贝</span>
              <span>温度</span>
              <span>湿度</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import AlertItem from '@/components/AlertItem.vue'
import EnvironmentIndicator from '@/components/EnvironmentIndicator.vue'
import { getRoomData } from '@/apis/data'
import { ElMessage } from 'element-plus'

// 模拟数据
const toilet = ref({
  id: 1,
  name: '中央广场智慧厕所',
  status: 'normal',
  male: {
    total: 8,
    occupied: 5
  },
  female: {
    total: 10,
    occupied: 7
  },
  todayVisits: 243,
  avgStayTime: 4.5,
  peakHour: '10:00-12:00',
  rating: 4.2,
  comments: [
    { user: '张先生', time: '2小时前', content: '卫生状况很好，洗手液充足' },
    { user: '李女士', time: '今天', content: '女厕排队时间有点长，建议增加隔间' },
    { user: '王女士', time: '昨天', content: '很干净，没有异味' }
  ],
  alerts: [
    { id: 1, type: 'water', message: '男厕3号位冲水故障', time: '15分钟前', level: 'high' },
    { id: 2, type: 'supply', message: '女厕纸巾即将用完', time: '1小时前', level: 'medium' },
    { id: 3, type: 'clean', message: '洗手台需要补充洗手液', time: '2小时前', level: 'low' }
  ]
})

const toiletStatus = computed(() => {
  switch (toilet.value.status) {
    case 'busy': return '使用高峰'
    case 'free': return '空闲中'
    case 'repair': return '维修中'
    default: return '正常使用'
  }
})

const statusClass = computed(() => {
  switch (toilet.value.status) {
    case 'busy': return 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200'
    case 'free': return 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200'
    case 'repair': return 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200'
    default: return 'bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200'
  }
})

// 厕所隔间数据
const maleStalls = ref([
  { x: 80, y: 100, occupied: false },
  { x: 80, y: 170, occupied: true },
  { x: 80, y: 240, occupied: false },
  { x: 130, y: 100, occupied: true },
  { x: 130, y: 170, occupied: true },
  { x: 130, y: 240, occupied: false },
  { x: 180, y: 100, occupied: false },
  { x: 180, y: 170, occupied: true }
])

const femaleStalls = ref([
  { x: 530, y: 100, occupied: true },
  { x: 530, y: 170, occupied: true },
  { x: 530, y: 240, occupied: false },
  { x: 580, y: 100, occupied: true },
  { x: 580, y: 170, occupied: false },
  { x: 580, y: 240, occupied: true },
  { x: 630, y: 100, occupied: true },
  { x: 630, y: 170, occupied: false },
  { x: 630, y: 240, occupied: true },
  { x: 680, y: 100, occupied: false }
])

// 环境图表数据
const envChartData = ref([
  { time: '8:00', value: 30 },
  { time: '10:00', value: 45 },
  { time: '12:00', value: 60 },
  { time: '14:00', value: 55 },
  { time: '16:00', value: 40 },
  { time: '18:00', value: 50 },
  { time: '20:00', value: 35 }
])

let pollInterval: number | null | undefined = null

onMounted(() => {
  const id = Number(new URLSearchParams(location.search).get('id')) || -1
  if (id === -1) {
    ElMessage({
      message: '未找到厕所信息',
      type: 'error'
    })
    return
  }
  fetchNewData(id);

  pollInterval = setInterval(() => {
    fetchNewData(id);
  }, 5000)
})

function fetchNewData(id: number) {
  getRoomData(id.toString()).then((res) => {
    console.log(res)
  })
}

onUnmounted(() => {
  // 组件卸载时清除定时器
  if (pollInterval) {
    clearInterval(pollInterval)
    pollInterval = null
  }
})
</script>

<style scoped>
/* 自定义样式 */
</style>
