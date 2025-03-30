<template>
    <div class="w-full h-screen bg-gray-100 dark:bg-gray-900 p-4 overflow-hidden">
        <!-- 顶部标题栏 -->
        <div class="flex justify-between items-center mb-4">
            <h1 class="text-3xl font-bold text-gray-800 dark:text-white">
                智慧厕所管理系统
                <span class="text-sm ml-2 bg-blue-500 text-white px-2 py-1 rounded">v2.0</span>
            </h1>
            <div class="flex items-center space-x-4">
                <div class="text-gray-600 dark:text-gray-300">
                    <span class="font-semibold">{{ currentTime }}</span>
                    <span class="ml-2">{{ currentDate }}</span>
                </div>
                <button @click="toggleDarkMode" class="p-2 rounded-full bg-white dark:bg-gray-700 shadow-md">
                    <svg v-if="isDark" xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-yellow-300" fill="none"
                        viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
                    </svg>
                    <svg v-else xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-gray-700" fill="none"
                        viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
                    </svg>
                </button>
            </div>
        </div>

        <div class="flex h-[calc(100vh-100px)] gap-4">
            <!-- 左侧数据面板 -->
            <div class="w-1/5 flex flex-col gap-4">
                <!-- 厕所统计卡片 -->
                <div class="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-4 flex-1">
                    <h3 class="text-lg font-semibold text-gray-700 dark:text-gray-200 mb-4">厕所统计</h3>
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
                    <h3 class="text-lg font-semibold text-gray-700 dark:text-gray-200 mb-4">环境监测</h3>
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

                <div class="absolute bottom-6 left-1/2 transform -translate-x-1/2 z-10 flex gap-2">
                    <button @click="zoomIn" class="p-2 bg-white dark:bg-gray-700 rounded-full shadow-md">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24"
                            stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                        </svg>
                    </button>
                    <button @click="zoomOut" class="p-2 bg-white dark:bg-gray-700 rounded-full shadow-md">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24"
                            stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 12H4" />
                        </svg>
                    </button>
                    <button @click="locateMe" class="p-2 bg-white dark:bg-gray-700 rounded-full shadow-md">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24"
                            stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                        </svg>
                    </button>
                </div>
            </div>

            <!-- 右侧信息面板 -->
            <div class="w-1/5 flex flex-col gap-4">
                <!-- 热门厕所排行 -->
                <div class="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-4 flex-1">
                    <h3 class="text-lg font-semibold text-gray-700 dark:text-gray-200 mb-4">热门厕所排行</h3>
                    <div class="space-y-3">
                        <RankItem v-for="(item, index) in topToilets" :key="item.id" :rank="index + 1" :name="item.name"
                            :count="item.usageCount" :trend="item.trend" />
                    </div>
                </div>

                <!-- 实时告警 -->
                <div class="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-4 flex-1">
                    <h3 class="text-lg font-semibold text-gray-700 dark:text-gray-200 mb-4">实时告警</h3>
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
                <span class="text-sm text-gray-600 dark:text-gray-300">系统运行正常</span>
            </div>
            <div class="text-sm text-gray-600 dark:text-gray-300">
                数据更新时间: {{ lastUpdateTime }}
            </div>
            <div class="flex items-center space-x-4">
                <span class="text-sm text-gray-600 dark:text-gray-300">总设备数: 256</span>
                <span class="text-sm text-gray-600 dark:text-gray-300">在线率: 98.4%</span>
            </div>
        </div>

        <!-- 厕所详情弹窗 -->
        <ToiletDetail v-if="showDetail" :toilet="selectedToilet" @close="showDetail = false" />
    </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import AMapLoader from '@amap/amap-jsapi-loader'

// 组件导入
import StatCard from '@/components/StatCard.vue'
import GaugeChart from '@/components/GaugeChart.vue'
import RankItem from '@/components/RankItem.vue'
import AlertItem from '@/components/AlertItem.vue'
import ToiletDetail from '@/components/ToiletDetail.vue'

// 暗黑模式
const isDark = ref(false)
const toggleDarkMode = () => {
    isDark.value = !isDark.value
}

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
    { id: 4, type: 'supply', message: '购物中心3F纸巾不足', time: '45分钟前', level: 'low' },
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
                            fillColor: isDark.value ? "#1E3A8A" : "#CCF3FF",
                            strokeColor: isDark.value ? "#7C3AED" : "#CC66CC",
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
                showDetail.value = true
            })

            map.add(marker)
        })
    } catch (e) {
        console.error("地图加载失败:", e)
    }
}

// 根据厕所状态获取图标
const getToiletIcon = (status) => {
    const baseUrl = 'https://webapi.amap.com/theme/v1.3/markers/'
    const size = 'mid'
    let color = 'blue'

    switch (status) {
        case 'busy':
            color = 'red'
            break
        case 'free':
            color = 'green'
            break
        case 'repair':
            color = 'gray'
            break
        default:
            color = 'blue'
    }

    return new AMap.Icon({
        image: `${baseUrl}n/mark_${color}.png`,
        size: new AMap.Size(24, 34),
        imageSize: new AMap.Size(24, 34)
    })
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