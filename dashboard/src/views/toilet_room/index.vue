<!-- eslint-disable vue/valid-v-for -->
<template>
  <div class="h-screen bg-gray-50 dark:bg-gray-900 p-4 overflow-auto text-white">
    <!-- 头部厕所名称和返回按钮 -->
    <div class="flex items-center justify-between mb-6">
      <button
        @click="gotoDashBoard()"
        class="flex items-center text-blue-500 hover:text-blue-700 dark:text-blue-400 dark:hover:text-blue-300"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-5 w-5 mr-1"
          viewBox="0 0 20 20"
          fill="currentColor"
        >
          <path
            fill-rule="evenodd"
            d="M9.707 16.707a1 1 0 01-1.414 0l-6-6a1 1 0 010-1.414l6-6a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l4.293 4.293a1 1 0 010 1.414z"
            clip-rule="evenodd"
          />
        </svg>
        返回
      </button>
      <h1 class="text-2xl font-bold dark:text-white text-center flex-1">
        {{ toiletData.name }}
        <span class="ml-2 text-sm px-2 py-1 rounded-full" :class="statusClass">
          {{ toiletStatus }}
        </span>
      </h1>
      <div class="w-10"></div>
      <!-- 占位保持对称 -->
    </div>

    <div class="flex flex-col lg:flex-row gap-6">
      <!-- 左侧数据面板 -->
      <div class="w-full lg:w-1/4 flex flex-col gap-6">
        <!-- 侧位占用统计 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-4">
          <h2 class="text-lg font-semibold dark:text-gray-200 mb-4 flex items-center">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-5 w-5 mr-2 text-blue-500"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
              />
            </svg>
            厕位占用统计
          </h2>

          <div class="grid grid-cols-2 gap-4 mb-4">
            <div class="text-center">
              <div class="text-3xl font-bold text-blue-500">
                {{ toilet.male.occupied }}/{{ toilet.male.total }}
              </div>
              <div class="text-sm text-gray-500 dark:text-gray-400">男厕占用</div>
              <div class="h-2 bg-gray-200 dark:bg-gray-700 rounded-full mt-2 overflow-hidden">
                <div
                  class="h-full bg-blue-500 rounded-full"
                  :style="{ width: `${(toilet.male.occupied / toilet.male.total) * 100}%` }"
                ></div>
              </div>
            </div>
            <div class="text-center">
              <div class="text-3xl font-bold text-pink-500">
                {{ toilet.female.occupied }}/{{ toilet.female.total }}
              </div>
              <div class="text-sm dark:text-gray-400">女厕占用</div>
              <div class="h-2 bg-gray-200 dark:bg-gray-700 rounded-full mt-2 overflow-hidden">
                <div
                  class="h-full bg-pink-500 rounded-full"
                  :style="{ width: `${(toilet.female.occupied / toilet.female.total) * 100}%` }"
                ></div>
              </div>
            </div>
          </div>

          <div class="border-t border-gray-200 dark:border-gray-700 pt-3">
            <h3 class="text-sm font-medium dark:text-gray-300 mb-2">客流数据</h3>
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
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-5 w-5 mr-2 text-green-500"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6"
              />
            </svg>
            环境监测
          </h2>

          <div class="space-y-4">
            <EnvironmentIndicator
              v-for="item in envDataList"
              :title="item.title"
              :value="item.value"
              :unit="item.unit"
              :level="item.level"
              :icon="item.icon"
            ></EnvironmentIndicator>
          </div>
        </div>
      </div>

      <!-- 中间主体区域 -->
      <div class="flex-1 flex flex-col gap-6 h-full">
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-4">
          <h2 class="text-lg font-semibold text-gray-700 dark:text-gray-200 mb-4">厕所导览图</h2>

          <div
            class="relative bg-gray-100 dark:bg-gray-700 rounded-lg overflow-hidden p-4 h-[440px]"
          >
            <LayoutPreview :layout-data="toiletData.design_map"></LayoutPreview>
          </div>
        </div>

        <!-- 底部区域 -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
          <!-- 满意度评价 -->
          <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-4">
            <h2
              class="text-lg font-semibold text-gray-700 dark:text-gray-200 mb-4 flex items-center"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5 mr-2 text-yellow-500"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M11.049 2.927c.3-.921 1.603-.921 1.902 0l1.519 4.674a1 1 0 00.95.69h4.915c.969 0 1.371 1.24.588 1.81l-3.976 2.888a1 1 0 00-.363 1.118l1.518 4.674c.3.922-.755 1.688-1.538 1.118l-3.976-2.888a1 1 0 00-1.176 0l-3.976 2.888c-.783.57-1.838-.197-1.538-1.118l1.518-4.674a1 1 0 00-.363-1.118l-3.976-2.888c-.784-.57-.38-1.81.588-1.81h4.914a1 1 0 00.951-.69l1.519-4.674z"
                />
              </svg>
              满意度评价
            </h2>

            <div class="flex items-center justify-between mb-3">
              <div class="text-3xl font-bold text-gray-800 dark:text-white">
                {{ toilet.rating }}<span class="text-xl text-gray-500">/5</span>
              </div>
              <div class="flex">
                <svg
                  v-for="i in 5"
                  :key="i"
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-6 w-6"
                  :class="
                    i <= Math.floor(toilet.rating)
                      ? 'text-yellow-400'
                      : 'text-gray-300 dark:text-gray-500'
                  "
                  viewBox="0 0 20 20"
                  fill="currentColor"
                >
                  <path
                    d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"
                  />
                </svg>
              </div>
            </div>

            <div class="space-y-2">
              <div
                v-for="(comment, index) in comments.slice(0, 2)"
                :key="index"
                class="border-b border-gray-200 dark:border-gray-700 pb-2"
              >
                <div class="flex items-center justify-between">
                  <span class="font-medium text-gray-700 dark:text-gray-300">{{
                    comment.username
                  }}</span>
                  <span class="text-sm text-gray-500">{{ comment.create_time }}</span>
                </div>
                <p class="text-gray-600 dark:text-gray-400 text-sm mt-1">
                  {{ comment.suggestion }}
                </p>
              </div>
            </div>

            <button
              class="mt-3 w-full py-2 bg-blue-50 dark:bg-gray-700 text-blue-500 dark:text-blue-400 rounded-lg hover:bg-blue-100 dark:hover:bg-gray-600 transition-colors text-sm"
            >
              查看全部评价
            </button>
          </div>

          <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md overflow-hidden">
            <div class="relative pt-[56.25%] bg-black">
              <video
                :src="videoUrl"
                id="video"
                muted
                alt="广告"
                autoplay
                loop
                class="absolute inset-0 w-full h-full object-cover opacity-70"
              />

              <div
                class="absolute bottom-2 left-2 bg-yellow-500 text-white text-xs px-2 py-1 rounded"
              >
                广告
              </div>
            </div>
            <div class="p-3">
              <h3 class="font-medium text-gray-700 dark:text-gray-300">城市文明如厕宣传</h3>
              <p class="text-sm text-gray-500 dark:text-gray-400 mt-1">
                倡导文明如厕，共建卫生城市
              </p>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧数据面板 -->
      <div class="w-full lg:w-1/4 flex flex-col gap-6">
        <!-- 警报信息 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-4">
          <h2 class="text-lg font-semibold text-gray-700 dark:text-gray-200 mb-4 flex items-center">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-5 w-5 mr-2 text-red-500"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"
              />
            </svg>
            警报信息
          </h2>

          <div class="space-y-3">
            <AlertItem
              v-for="alert in toilet.alerts"
              :key="alert.id"
              :type="alert.type"
              :message="alert.message"
              :time="alert.time"
              :level="alert.level"
            />
          </div>
        </div>

        <!-- 环境监测图表 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-4 flex-1">
          <h2 class="text-lg font-semibold text-gray-700 dark:text-gray-200 mb-4 flex items-center">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-5 w-5 mr-2 text-purple-500"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"
              />
            </svg>
            环境趋势
          </h2>

          <div class="h-64 p-4 bg-white dark:bg-gray-800 rounded-lg shadow">
            <EnvTrading></EnvTrading>
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
import LayoutPreview from './components/LayoutPreview.vue'
import EnvTrading from './components/EnvTrading.vue'

// 模拟数据
const toiletData = ref({})
const videoUrl = ref('') // 替换为实际视频链接
const environment_data = ref({})
const alarms = ref([])
const comments = ref([])
const traffic = ref([])

const envDataList = computed(() => {
  if (!environment_data.value) return []
  return [
    {
      title: '二氧化碳',
      value: environment_data.value.co2,
      unit: 'ppm',
      level: 'normal',
      icon: '.CO₂',
    },
    {
      title: '氨气',
      value: environment_data.value.ammonia,
      unit: 'ppm',
      level: 'normal',
      icon: 'NH₃',
    },
    {
      title: '硫化氢',
      value: environment_data.value.h2s,
      unit: 'ppm',
      level: 'normal',
      icon: 'H₂S',
    },
    {
      title: '湿度',
      value: environment_data.value.humidity,
      unit: '%',
      level: 'normal',
      icon: '💧',
    },
    {
      title: 'PM10',
      value: environment_data.value.pm10,
      unit: 'μg/m³',
      level: 'normal',
      icon: '🌫️',
    },
    {
      title: 'PM2.5',
      value: environment_data.value.pm2d5,
      unit: 'μg/m³',
      level: 'normal',
      icon: '🌫️',
    },
    {
      title: '气压',
      value: environment_data.value.pressure,
      unit: 'hPa',
      level: 'normal',
      icon: ' AP',
    },
    {
      title: '温度',
      value: environment_data.value.temperature,
      unit: '℃',
      level: 'normal',
      icon: '🌡️',
    },
    {
      title: '总挥发性有机物',
      value: environment_data.value.tvoc,
      unit: 'ppm',
      level: 'normal',
      icon: 'TVOC',
    },
  ]
})

function getEnvStatusClass(status) {
  const statusClasses = {
    优秀: 'text-green-600 dark:text-green-400',
    良好: 'text-blue-600 dark:text-blue-400',
    一般: 'text-yellow-600 dark:text-yellow-400',
    较差: 'text-orange-600 dark:text-orange-400',
    恶劣: 'text-red-600 dark:text-red-400',
  }
  return statusClasses[status] || 'text-gray-600 dark:text-gray-300'
}
const envChartData = [
  {
    time: '08:00',
    odor: 30,
    odorValue: 2.1,
    noise: 40,
    noiseValue: 52,
    temperature: 50,
    tempValue: 22,
    humidity: 60,
    humidityValue: 65,
  },
  {
    time: '10:00',
    odor: 45,
    odorValue: 3.2,
    noise: 60,
    noiseValue: 68,
    temperature: 60,
    tempValue: 24,
    humidity: 55,
    humidityValue: 60,
  },
  {
    time: '12:00',
    odor: 60,
    odorValue: 4.5,
    noise: 70,
    noiseValue: 72,
    temperature: 70,
    tempValue: 26,
    humidity: 50,
    humidityValue: 55,
  },
  {
    time: '14:00',
    odor: 55,
    odorValue: 4.1,
    noise: 65,
    noiseValue: 70,
    temperature: 75,
    tempValue: 27,
    humidity: 45,
    humidityValue: 50,
  },
  {
    time: '16:00',
    odor: 40,
    odorValue: 3.0,
    noise: 50,
    noiseValue: 60,
    temperature: 65,
    tempValue: 25,
    humidity: 55,
    humidityValue: 60,
  },
  {
    time: '18:00',
    odor: 35,
    odorValue: 2.5,
    noise: 45,
    noiseValue: 58,
    temperature: 55,
    tempValue: 23,
    humidity: 60,
    humidityValue: 65,
  },
]
const currentEnvStatus = '良好'
const toilet = ref({
  id: 1,
  name: '中央广场智慧厕所',
  status: 'normal',
  male: {
    total: 6,
    occupied: 2,
  },
  female: {
    total: 5,
    occupied: 1,
  },
  todayVisits: 243,
  avgStayTime: 4.5,
  peakHour: '10:00-12:00',
  rating: 4.2,
  alerts: [
    // { id: 1, type: 'water', message: '男厕3号位冲水故障', time: '15分钟前', level: 'high' },
    { id: 2, type: 'supply', message: '纸巾即将用完', time: '1小时前', level: 'medium' },
    // { id: 3, type: 'clean', message: '洗手台需要补充洗手液', time: '2小时前', level: 'low' },
  ],
})

function gotoDashBoard() {
  window.location.href = '/page/dashboard'
}

const toiletStatus = computed(() => {
  switch (toilet.value.status) {
    case 'busy':
      return '使用高峰'
    case 'free':
      return '空闲中'
    case 'repair':
      return '维修中'
    default:
      return '正常使用'
  }
})

const statusClass = computed(() => {
  switch (toilet.value.status) {
    case 'busy':
      return 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200'
    case 'free':
      return 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200'
    case 'repair':
      return 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200'
    default:
      return 'bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200'
  }
})

let pollInterval: number | null | undefined = null

onMounted(async () => {
  const id = Number(new URLSearchParams(location.search).get('id')) || -1
  if (id === -1) {
    ElMessage({
      message: '未找到厕所信息',
      type: 'error',
    })
    return
  }
  const data = await fetchNewData(id)
  const videoElement = document.getElementById('video') as HTMLVideoElement
  if (videoElement) {
    videoElement.src = data.video_url
    videoElement.play()
  }

  pollInterval = setInterval(() => {
    fetchNewData(id)
  }, 5000)
})

async function fetchNewData(id: number) {
  return getRoomData(id.toString()).then((res) => {
    toiletData.value = res.data.toilet
    comments.value = res.data.comments
    traffic.value = res.data.traffic_data
    alarms.value = res.data.alarms
    environment_data.value = res.data.environment_data[0]
    // console.log('获取厕所数据:', environment_data.value)
    return res.data
  })
}

onUnmounted(() => {
  if (pollInterval) {
    clearInterval(pollInterval)
    pollInterval = null
  }
})
</script>

<style scoped>
/* 自定义样式 */
</style>
