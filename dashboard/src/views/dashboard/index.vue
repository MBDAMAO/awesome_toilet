<template>
  <div class="w-full h-full bg-gray-100 dark:bg-gray-900 p-4 text-white">
    <!-- 顶部标题栏 -->
    <div class="flex justify-between items-center mb-4">
      <h1 class="text-3xl font-bold dark:text-white">智慧公厕管理系统</h1>
      <div class="flex items-center space-x-4">
        <div class="text-gray-600 dark:text-gray-300">
          <span class="font-semibold">{{ currentTime }}</span>
          <span class="ml-2">{{ currentDate }}</span>
        </div>
        <div
          class="h-full hover:text-blue-400 cursor-pointer"
          @click="openNewTab('http://127.0.0.1:18014')"
        >
          前往管理界面
        </div>
      </div>
    </div>

    <div class="flex h-full gap-4 text-white">
      <!-- 左侧数据面板 -->
      <div class="w-1/5 flex flex-col gap-4">
        <!-- 客流分析图表 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-4 flex-1">
          <h3 class="text-lg font-semibold text-white mb-4">客流分析</h3>
          <div class="h-64">
            <VueECharts :option="passengerFlowOption" class="w-full h-full" />
          </div>
        </div>

        <!-- 今日水电用量 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-4 flex-1">
          <h3 class="text-lg font-semibold dark:text-gray-200 mb-4">今日水电用量</h3>
          <div class="h-64">
            <VueECharts :option="todayUsageOption" class="w-full h-full" />
          </div>
        </div>

        <!-- 本月水电用量 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-4 flex-1">
          <h3 class="text-lg font-semibold dark:text-white mb-4">本月水电用量</h3>
          <div class="grid grid-cols-2 gap-4 h-full">
            <CircularProgress
              title="本月用电量"
              :value="85"
              unit="kWh"
              color="text-orange-500"
              class="h-full"
            />
            <CircularProgress
              title="本月用水量"
              :value="62"
              unit="吨"
              color="text-blue-500"
              class="h-full"
            />
          </div>
        </div>
      </div>

      <!-- 中间地图区域 -->
      <div class="flex-1 flex flex-col">
        <!-- 客流指标卡 -->
        <div class="grid grid-cols-4 gap-4 mb-4">
          <StatCard
            title="男厕日客流量"
            :value="maleDailyCount"
            icon="♂️"
            :trend="maleDailyTrend"
            :change="maleDailyChange"
            class="bg-blue-50 dark:bg-blue-900/30"
          />
          <StatCard
            title="男厕总客流量"
            :value="maleTotalCount"
            icon="♂️"
            trend="none"
            class="bg-blue-100 dark:bg-blue-900/50"
          />
          <StatCard
            title="女厕日客流量"
            :value="femaleDailyCount"
            icon="♀️"
            :trend="femaleDailyTrend"
            :change="femaleDailyChange"
            class="bg-pink-50 dark:bg-pink-900/30"
          />
          <StatCard
            title="女厕总客流量"
            :value="femaleTotalCount"
            icon="♀️"
            trend="none"
            class="bg-pink-100 dark:bg-pink-900/50"
          />
        </div>

        <!-- 地图 -->
        <div
          id="map"
          class="h-full w-full relative rounded-xl overflow-hidden bg-transparent"
          style="box-shadow: 0 2px 10px 0 rgba(14, 33, 39, 0.2)"
        ></div>
      </div>

      <!-- 右侧信息面板 -->
      <div class="w-1/5 flex flex-col gap-4">
        <!-- 告警栏 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-4 flex-1">
          <h3 class="text-lg font-semibold dark:text-gray-200 mb-4">实时告警</h3>
          <div class="space-y-3 max-h-80 overflow-y-auto">
            <AlertItem
              v-for="alert in alerts"
              :key="alert.id"
              :type="alert.type"
              :message="alert.message"
              :time="alert.time"
              :level="alert.level"
            />
          </div>
        </div>

        <!-- 用纸量剩余 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-4 flex-1">
          <h3 class="text-lg font-semibold dark:text-gray-200 mb-4">用纸量剩余</h3>
          <div class="h-48">
            <VueECharts ts :option="paperRemainOption" class="w-full h-full" />
          </div>
          <div class="mt-2 text-sm text-gray-600 dark:text-gray-400">
            预计更换时间: {{ nextPaperReplacement }}
          </div>
        </div>

        <!-- 服务评价 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-4 flex-1">
          <h3 class="text-lg font-semibold dark:text-gray-200 mb-4">服务评价</h3>
          <div class="flex flex-col items-center justify-center h-48">
            <div class="text-4xl font-bold text-yellow-500 mb-2">
              {{ averageRating.toFixed(1) }}<span class="text-2xl">/5.0</span>
            </div>
            <div class="flex mb-4">
              <StarIcon
                v-for="i in 5"
                :key="i"
                :filled="i <= Math.round(averageRating)"
                class="w-6 h-6"
              />
            </div>
            <div class="h-24 w-full">
              <VueECharts :option="ratingWordCloudOption" class="w-full h-full" />
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 底部状态栏 -->
    <div
      class="mt-4 bg-white dark:bg-gray-800 rounded-xl shadow-lg p-3 flex justify-between items-center"
    >
      <div class="flex items-center space-x-2">
        <div class="w-3 h-3 rounded-full bg-green-500"></div>
        <span class="text-sm dark:text-gray-300">系统运行正常</span>
      </div>
      <div class="text-sm text-gray-600 dark:text-gray-300">数据更新时间: {{ lastUpdateTime }}</div>
      <div class="flex items-center space-x-4">
        <span class="text-sm dark:text-gray-300">总公厕数: {{ totalToilets }}</span>
        <span class="text-sm dark:text-gray-300">设备在线率: {{ onlineRate }}%</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue'
import AMapLoader from '@amap/amap-jsapi-loader'
import 'echarts-wordcloud'

// 组件导入
import StatCard from '@/components/StatCard.vue'
import CircularProgress from '@/components/CircularProgress.vue'
import AlertItem from '@/components/AlertItem.vue'
import StarIcon from '@/components/StarIcon.vue'
import { getHomeData } from '@/apis/data'

// 时间显示
const currentTime = ref('')
const currentDate = ref('')
const lastUpdateTime = ref('')

const updateTime = () => {
  const now = new Date()
  currentTime.value = now.toLocaleTimeString()
  currentDate.value = now.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    weekday: 'long',
  })
  lastUpdateTime.value = now.toLocaleString('zh-CN')
}

const maleDailyCount = ref(428)
const maleTotalCount = ref(12560)
const maleDailyTrend = ref('up')
const maleDailyChange = ref('12%')
const femaleDailyCount = ref(386)
const femaleTotalCount = ref(11875)
const femaleDailyTrend = ref('up')
const femaleDailyChange = ref('8%')

const passengerFlowOption = ref({
  tooltip: {
    trigger: 'axis',
    axisPointer: { type: 'shadow' },
  },
  legend: {
    data: ['男厕', '女厕'],
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true,
  },
  xAxis: {
    type: 'category',
    data: ['8:00', '10:00', '12:00', '14:00', '16:00', '18:00', '20:00'],
  },
  yAxis: { type: 'value' },
  series: [
    {
      name: '男厕',
      type: 'bar',
      data: [45, 78, 92, 65, 53, 76, 42],
      itemStyle: { color: '#3b82f6' },
    },
    {
      name: '女厕',
      type: 'bar',
      data: [38, 65, 84, 58, 47, 68, 36],
      itemStyle: { color: '#ec4899' },
    },
  ],
})

const todayUsageOption = ref({
  tooltip: {
    trigger: 'axis',
    axisPointer: { type: 'cross' },
  },
  legend: {
    data: ['用电量', '用水量'],
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true,
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['0:00', '3:00', '6:00', '9:00', '12:00', '15:00', '18:00', '21:00'],
  },
  yAxis: [
    {
      type: 'value',
      name: '用电量(kWh)',
      position: 'left',
    },
    {
      type: 'value',
      name: '用水量(吨)',
      position: 'right',
    },
  ],
  series: [
    {
      name: '用电量',
      type: 'line',
      data: [2.3, 1.8, 1.5, 3.2, 4.5, 3.8, 3.2, 2.7],
      yAxisIndex: 0,
      itemStyle: { color: '#f59e0b' },
    },
    {
      name: '用水量',
      type: 'line',
      data: [0.8, 0.6, 0.5, 1.2, 1.8, 1.5, 1.2, 0.9],
      yAxisIndex: 1,
      itemStyle: { color: '#60a5fa' },
    },
  ],
})

const paperRemainOption = ref({
  series: [
    {
      type: 'gauge',
      center: ['50%', '60%'],
      startAngle: 180,
      endAngle: 0,
      min: 0,
      max: 100,
      splitNumber: 10,
      radius: '100%',
      axisLine: {
        lineStyle: {
          width: 30,
          color: [
            [0.3, '#ef4444'],
            [0.7, '#f59e0b'],
            [1, '#10b981'],
          ],
        },
      },
      pointer: { itemStyle: { color: 'auto' } },
      axisTick: { distance: -30, length: 8, lineStyle: { color: '#fff', width: 2 } },
      splitLine: { distance: -30, length: 30, lineStyle: { color: '#fff', width: 4 } },
      axisLabel: { color: 'auto', distance: 40, fontSize: 12 },
      detail: {
        valueAnimation: true,
        formatter: '{value}%',
        color: 'inherit',
        fontSize: 24,
      },
      data: [{ value: 68 }],
    },
  ],
})

const ratingWordCloudOption = ref({
  series: [
    {
      type: 'wordCloud',
      shape: 'circle',
      left: 'center',
      top: 'center',
      width: '100%',
      height: '100%',
      right: null,
      bottom: null,
      sizeRange: [12, 32],
      rotationRange: [0, 0],
      rotationStep: 45,
      gridSize: 8,
      drawOutOfBound: false,
      textStyle: {
        fontFamily: 'sans-serif',
        fontWeight: 'bold',
        color: function () {
          return (
            'rgb(' +
            [
              Math.round(Math.random() * 160 + 95),
              Math.round(Math.random() * 160 + 95),
              Math.round(Math.random() * 160 + 95),
            ].join(',') +
            ')'
          )
        },
      },
      emphasis: {
        textStyle: {
          shadowBlur: 10,
          shadowColor: '#333',
        },
      },
      data: [
        { value: 45, name: '干净整洁' },
        { value: 38, name: '无异味' },
        { value: 32, name: '设施完好' },
        { value: 28, name: '方便快捷' },
        { value: 25, name: '卫生良好' },
        { value: 20, name: '环境舒适' },
        { value: 18, name: '管理到位' },
        { value: 15, name: '服务周到' },
      ],
    },
  ],
})

// 系统数据
const totalToilets = ref(128)
const onlineRate = ref(98.4)
const averageRating = ref(4.2)
const nextPaperReplacement = ref('2023-12-15 14:00')

// 告警数据
const alerts = ref([
  { id: 1, type: 'paper', message: '中央广场用纸量不足20%', time: '15分钟前', level: 'medium' },
  { id: 2, type: 'water', message: '地铁站A出口测试用水量异常', time: '32分钟前', level: 'high' },
  { id: 3, type: 'electric', message: '购物中心3F用电量异常', time: '1小时前', level: 'medium' },
  // { id: 4, type: 'clean', message: '公园东门需要紧急清洁', time: '2小时前', level: 'low' }s
])

// 地图相关
let map = null
function openNewTab(url: string) {
  window.open(url, '_blank')
}

// 初始化地图（保持原有地图代码不变）
const initMap = async () => {
  try {
    const AMap = await AMapLoader.load({
      key: 'a4ed2acdb87e1fb1bfce40e716e48ed1',
      version: '2.0',
      plugins: ['AMap.Scale'],
    })

    const style = 'amap://styles/darkblue'
    const map = new AMap.Map('map', {
      zoom: 16,
      center: [104.097532, 30.674544],
      mapStyle: style,
    })

    AMap.plugin('AMap.DistrictSearch', function () {
      const district = new AMap.DistrictSearch({
        extensions: 'all',
        level: 'district',
      })
      district.search('成华区', function (status, result) {
        const bounds = result.districtList[0].boundaries
        if (bounds) {
          for (let i = 0; i < bounds.length; i++) {
            const polygon = new AMap.Polygon({
              map: map, //显示该覆盖物的地图对象
              strokeWeight: 1, //轮廓线宽度
              path: bounds[i], //多边形轮廓线的节点坐标数组
              fillOpacity: 0.7, //多边形填充透明度
              fillColor: '#CCF3FF', //多边形填充颜色
              strokeColor: '#CC66CC', //线条颜色
            })
          }
          map.setFitView() //将覆盖物调整到合适视野
        }
      })
    })

    const markers = [
      { position: [104.100221, 30.675709], name: '中央广场厕所', status: 'busy', id: 1 },
      { position: [104.095432, 30.692345], name: '地铁站A出口', status: 'normal', id: 2 },
      { position: [104.102345, 30.658901], name: '购物中心3F', status: 'free', id: 3 },
      { position: [104.158765, 30.671234], name: '公园东门', status: 'repair', id: 4 },
      { position: [104.141234, 30.673456], name: '办公楼1F', status: 'free', id: 5 },
      { position: [104.136543, 30.686789], name: '商业街2号', status: 'normal', id: 6 },
      { position: [104.129876, 30.670123], name: '体育场西门', status: 'free', id: 7 },
    ]
    markers.forEach((toilet) => {
      // const icon = getToiletIcon(toilet.status)
      const marker = new AMap.Marker({
        position: toilet.position,
        title: toilet.name,
        // icon: icon,
        extData: toilet,
      })

      marker.on('click', (e) => {
        gotoRoom(e.target.getExtData().id)
      })

      map.add(marker)
    })
  } catch (e) {
    console.error('地图加载失败:', e)
  }
}

const gotoRoom = (id) => {
  window.location.href = `/page/room?id=${id}`
}

// 生命周期
onMounted(() => {
  updateTime()
  initMap()
  getHomeData().then((res) => {
    console.log(res)
  })
  setInterval(updateTime, 1000)
})

onBeforeUnmount(() => {
  if (map) {
    map.destroy()
    map = null
  }
})
</script>

<style>
/* 保持原有样式不变 */
::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.1);
  border-radius: 3px;
}

::-webkit-scrollbar-thumb {
  background: rgba(0, 0, 0, 0.2);
  border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 0, 0, 0.3);
}

.dark ::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.1);
}

.dark ::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.2);
}

.dark ::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.3);
}
</style>
