<template>
  <div class="w-full h-full bg-gray-100 dark:bg-gray-900 p-4 text-white">
    <!-- 顶部标题栏 -->
    <div class="flex justify-between items-center mb-4">
      <h1 class="text-3xl font-bold dark:text-white">
        智慧厕所系统
      </h1>
      <div class="flex items-center space-x-4">
        <div class="text-gray-600 dark:text-gray-300">
          <span class="font-semibold">{{ currentTime }}</span>
          <span class="ml-2">{{ currentDate }}</span>
        </div>

        <div class="h-full hover:text-blue-400 cursor-pointer" @click="openNewTab('http://127.0.0.1:18014')">前往管理界面
        </div>
      </div>
    </div>

    <div class="flex h-full gap-4">
      <!-- 左侧数据面板 -->
      <div class="w-1/5 flex flex-col gap-4">
        <!-- 厕所统计卡片 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-4 flex-1">
          <h3 class="text-lg font-semibold dark:text-gray-200 mb-4">厕所统计</h3>
          <div class="grid grid-cols-2 gap-4">
            <StatCard title="总厕所数" value="128" icon="🚻" trend="up" change="5%"
              class="bg-blue-50 dark:bg-blue-900/30" />
            <StatCard title="使用中" value="87" icon="🧻" trend="up" change="3%"
              class="bg-green-50 dark:bg-green-900/30" />
            <StatCard title="空闲中" value="41" icon="🆓" trend="down" change="2%"
              class="bg-purple-50 dark:bg-purple-900/30" />
            <StatCard title="维修中" value="5" icon="🛠️" trend="down" change="1%"
              class="bg-yellow-50 dark:bg-yellow-900/30" />
          </div>
        </div>

        <!-- 实时监测 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-4 flex-1">
          <h3 class="text-lg font-semibold dark:text-gray-200 mb-4">环境监测</h3>
          <div class="space-y-4">
            <GaugeChart title="空气质量" value="85" unit="AQI" color="text-green-500" />
            <GaugeChart title="温度" value="26" unit="°C" color="text-orange-500" />
            <GaugeChart title="湿度" value="65" unit="%" color="text-blue-500" />
          </div>
        </div>
      </div>

      <!-- 中间地图区域 -->
      <div class="flex-1 flex flex-col">
        <div id="map" class="h-full w-full relative rounded-xl overflow-hidden bg-transparent"
          style="box-shadow: 0 2px 10px 0 rgba(14, 33, 39, .2);"></div>
      </div>

      <!-- 右侧信息面板 -->
      <div class="w-1/5 flex flex-col gap-4">
        <!-- 热门厕所排行 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-4 flex-1">
          <h3 class="text-lg font-semibold dark:text-gray-200 mb-4">热门厕所排行</h3>
          <div class="space-y-3">
            <RankItem v-for="(item, index) in topToilets" :key="item.id" :rank="index + 1" :name="item.name"
              :count="item.usageCount" :trend="item.trend" />
          </div>
        </div>

        <!-- 实时告警 -->
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-4 flex-1">
          <h3 class="text-lg font-semibold dark:text-gray-200 mb-4">实时告警</h3>
          <div class="space-y-3">
            <AlertItem v-for="alert in alerts" :key="alert.id" :type="alert.type" :message="alert.message"
              :time="alert.time" :level="alert.level" />
          </div>
        </div>
      </div>
    </div>

    <!-- 底部状态栏 -->
    <div class="mt-4 bg-white dark:bg-gray-800 rounded-xl shadow-lg p-3 flex justify-between items-center">
      <div class="flex items-center space-x-2">
        <div class="w-3 h-3 rounded-full bg-green-500"></div>
        <span class="text-sm dark:text-gray-300">系统运行正常</span>
      </div>
      <div class="text-sm text-gray-600 dark:text-gray-300">
        数据更新时间: {{ lastUpdateTime }}
      </div>
      <div class="flex items-center space-x-4">
        <span class="text-sm dark:text-gray-300">总设备数: 256</span>
        <span class="text-sm dark:text-gray-300">在线率: 98.4%</span>
      </div>
    </div>

    <!-- 厕所详情弹窗 -->
    <ToiletDetail v-if="showDetail" :toilet="selectedToilet" @close="showDetail = false" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue'
import AMapLoader from '@amap/amap-jsapi-loader'

// 组件导入
import StatCard from '@/components/StatCard.vue'
import GaugeChart from '@/components/GaugeChart.vue'
import RankItem from '@/components/RankItem.vue'
import AlertItem from '@/components/AlertItem.vue'
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
    weekday: 'long'
  })
  lastUpdateTime.value = now.toLocaleString('zh-CN')
}

// 地图相关
let map = null
const showDetail = ref(false)
const selectedToilet = ref(null)
function openNewTab(url: string) {
  window.open(url, '_blank');
}

// 模拟数据
const topToilets = ref([
  { id: 1, name: '中央广场厕所', usageCount: 342, trend: 'up' },
  { id: 2, name: '地铁站A出口', usageCount: 298, trend: 'up' },
  { id: 3, name: '购物中心3F', usageCount: 276, trend: 'down' },
  { id: 4, name: '公园东门', usageCount: 231, trend: 'up' },
  { id: 5, name: '办公楼1F', usageCount: 187, trend: 'stable' },
])

const alerts = ref([
  { id: 1, type: 'water', message: '3号厕所水位异常', time: '2分钟前', level: 'high' },
  { id: 2, type: 'clean', message: '中央广场厕所需要清洁', time: '15分钟前', level: 'medium' },
  { id: 3, type: 'repair', message: '地铁站A出口门锁故障', time: '32分钟前', level: 'high' },
])
window._AMapSecurityConfig = {
  securityJsCode: "0ffd599e5ab5b239d14706e319275e94",
};
// 初始化地图
const initMap = async () => {
  try {
    const AMap = await AMapLoader.load({
      key: "a4ed2acdb87e1fb1bfce40e716e48ed1",
      version: "2.0",
      plugins: ["AMap.Scale"],
    })

    let style = 'amap://styles/darkblue'
    map = new AMap.Map("map", {
      zoom: 16,
      center: [104.097532, 30.674544],
      mapStyle: style
    })

    // 行政区划
    AMap.plugin("AMap.DistrictSearch", function () {
      var district = new AMap.DistrictSearch({
        extensions: "all",
        level: "district",
      })
      district.search("成华区", function (status, result) {
        var bounds = result.districtList[0].boundaries
        if (bounds) {
          for (var i = 0; i < bounds.length; i++) {
            var polygon = new AMap.Polygon({
              map: map,
              strokeWeight: 1,
              path: bounds[i],
              fillOpacity: 0.4,
              fillColor: "#1E3A8A",
              strokeColor: "#7C3AED"
            })
          }
          map.setFitView()
        }
      })
    })

    const markers = [
      { position: [104.100221, 30.675709], name: "中央广场厕所", status: "busy", id: 1 },
      { position: [104.095432, 30.692345], name: "地铁站A出口", status: "normal", id: 2 },
      { position: [104.102345, 30.658901], name: "购物中心3F", status: "free", id: 3 },
      { position: [104.158765, 30.671234], name: "公园东门", status: "repair", id: 4 },
      { position: [104.141234, 30.673456], name: "办公楼1F", status: "free", id: 5 },
      { position: [104.136543, 30.686789], name: "商业街2号", status: "normal", id: 6 },
      { position: [104.129876, 30.670123], name: "体育场西门", status: "free", id: 7 },
    ]
    markers.forEach(toilet => {
      // const icon = getToiletIcon(toilet.status)
      const marker = new AMap.Marker({
        position: toilet.position,
        title: toilet.name,
        // icon: icon,
        extData: toilet
      })

      marker.on('click', (e) => {
        selectedToilet.value = e.target.getExtData()
        gotoRoom(selectedToilet.value.id)
      })

      map.add(marker)
    })
  } catch (e) {
    console.error("地图加载失败:", e)
  }
}

const gotoRoom = (id) => {
  // 路由到/room
  window.location.href = `/page/room?id=${id}`
}

// 地图操作
const zoomIn = () => {
  if (map) map.zoomIn()
}

const zoomOut = () => {
  if (map) map.zoomOut()
}

const locateMe = () => {
  if (map) {
    map.plugin('AMap.Geolocation', () => {
      const geolocation = new AMap.Geolocation({
        enableHighAccuracy: true,
        timeout: 10000,
        buttonPosition: 'RB',
        buttonOffset: new AMap.Pixel(10, 20),
        zoomToAccuracy: true
      })
      map.addControl(geolocation)
      geolocation.getCurrentPosition()
    })
  }
}

// 生命周期
onMounted(() => {
  updateTime()
  initMap()
  getHomeData().then(res => {
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
/* 自定义滚动条 */
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
