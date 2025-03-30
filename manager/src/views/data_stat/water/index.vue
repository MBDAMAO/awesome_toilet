<template>
    <div class="p-6  min-h-screen">
        <div class="max-w-7xl mx-auto">
            <!-- 标题和筛选区域 -->
            <div class="flex justify-between items-center mb-6">
                <h1 class="text-2xl font-bold ">用水量统计分析</h1>
                <div class="flex space-x-4">
                    <el-select v-model="timeRange" placeholder="选择时间范围" class="w-40">
                        <el-option label="今日" value="today"></el-option>
                        <el-option label="本周" value="week"></el-option>
                        <el-option label="本月" value="month"></el-option>
                        <el-option label="自定义" value="custom"></el-option>
                    </el-select>

                    <el-date-picker v-if="timeRange === 'custom'" v-model="customDateRange" type="daterange"
                        range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" class="w-64" />

                    <el-select v-model="selectedLocation" placeholder="选择区域" class="w-40">
                        <el-option v-for="location in locations" :key="location.value" :label="location.label"
                            :value="location.value"></el-option>
                    </el-select>

                    <el-button type="primary" @click="fetchData">查询</el-button>
                </div>
            </div>

            <!-- 统计卡片 -->
            <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-6">
                <div class=" p-6 rounded-lg shadow">
                    <div class=" text-sm">今日用水量</div>
                    <div class="text-2xl font-bold mt-2">{{ formatNumber(todayUsage) }}<span
                            class="text-sm ml-1">吨</span></div>
                    <div class="flex items-center mt-2">
                        <span :class="['text-sm', todayTrend > 0 ? 'text-red-500' : 'text-green-500']">
                            {{ todayTrend > 0 ? '↑' : '↓' }} {{ Math.abs(todayTrend) }}%
                        </span>
                        <span class=" text-sm ml-1">较昨日</span>
                    </div>
                </div>

                <div class=" p-6 rounded-lg shadow">
                    <div class=" text-sm">本月用水量</div>
                    <div class="text-2xl font-bold mt-2">{{ formatNumber(monthUsage) }}<span
                            class="text-sm ml-1">吨</span></div>
                    <div class="flex items-center mt-2">
                        <span :class="['text-sm', monthTrend > 0 ? 'text-red-500' : 'text-green-500']">
                            {{ monthTrend > 0 ? '↑' : '↓' }} {{ Math.abs(monthTrend) }}%
                        </span>
                        <span class=" text-sm ml-1">较上月</span>
                    </div>
                </div>

                <div class=" p-6 rounded-lg shadow">
                    <div class=" text-sm">平均日用水量</div>
                    <div class="text-2xl font-bold mt-2">{{ formatNumber(avgDailyUsage) }}<span
                            class="text-sm ml-1">吨</span></div>
                    <div class="text-sm mt-2">本月数据</div>
                </div>

                <div class=" p-6 rounded-lg shadow">
                    <div class=" text-sm">用水异常次数</div>
                    <div class="text-2xl font-bold mt-2">{{ abnormalCount }}</div>
                    <div class=" text-sm mt-2">本月累计</div>
                </div>
            </div>

            <!-- 图表区域 -->
            <div class="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-6">
                <!-- 每日用水趋势 -->
                <div class=" p-6 rounded-lg shadow lg:col-span-2">
                    <div class="flex justify-between items-center mb-4">
                        <h2 class="text-lg font-semibold ">每日用水趋势</h2>
                        <el-radio-group v-model="dailyChartType" size="small">
                            <el-radio-button label="line">折线图</el-radio-button>
                            <el-radio-button label="bar">柱状图</el-radio-button>
                        </el-radio-group>
                    </div>
                    <div class="h-80">
                        <v-chart :option="dailyChartOption" autoresize />
                    </div>
                </div>

                <!-- 用水分布 -->
                <div class=" p-6 rounded-lg shadow">
                    <h2 class="text-lg font-semibold mb-4">用水区域分布</h2>
                    <div class="h-80">
                        <v-chart :option="distributionChartOption" autoresize />
                    </div>
                </div>
            </div>

            <!-- 时段分析 -->
            <div class=" p-6 rounded-lg shadow mb-6">
                <h2 class="text-lg font-semibold  mb-4">时段用水分析</h2>
                <div class="h-80">
                    <v-chart :option="hourlyChartOption" autoresize />
                </div>
            </div>

            <!-- 数据表格 -->
            <div class=" p-6 rounded-lg shadow">
                <div class="flex justify-between items-center mb-4">
                    <h2 class="text-lg font-semibold ">详细数据</h2>
                    <el-button type="primary" size="small" @click="exportData">导出数据</el-button>
                </div>
                <el-table :data="tableData" border style="width: 100%">
                    <el-table-column prop="date" label="日期" width="120" />
                    <el-table-column prop="location" label="区域" width="120" />
                    <el-table-column prop="usage" label="用水量(吨)" width="120" />
                    <el-table-column prop="cost" label="费用(元)" width="120" />
                    <el-table-column prop="avgTemperature" label="平均温度(℃)" width="140" />
                    <el-table-column prop="status" label="状态">
                        <template #default="scope">
                            <el-tag :type="scope.row.status === '正常' ? 'success' : 'danger'" size="small">
                                {{ scope.row.status }}
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="120">
                        <template #default="scope">
                            <el-button type="text" size="small" @click="showDetail(scope.row)">详情</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="flex justify-end mt-4">
                    <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize" :total="totalItems"
                        :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper"
                        @size-change="fetchTableData" @current-change="fetchTableData" />
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers'
import { LineChart, BarChart, PieChart } from 'echarts/charts'
import {
    TitleComponent,
    TooltipComponent,
    LegendComponent,
    GridComponent,
    DatasetComponent
} from 'echarts/components'
import VChart from 'vue-echarts'
import * as echarts from 'echarts'

// 注册必要的ECharts组件
use([
    CanvasRenderer,
    LineChart,
    BarChart,
    PieChart,
    TitleComponent,
    TooltipComponent,
    LegendComponent,
    GridComponent,
    DatasetComponent
])

// 筛选条件
const timeRange = ref('week')
const customDateRange = ref([])
const selectedLocation = ref('all')
const locations = ref([
    { label: '全部区域', value: 'all' },
    { label: 'A区', value: 'A' },
    { label: 'B区', value: 'B' },
    { label: 'C区', value: 'C' }
])

// 统计数据
const todayUsage = ref(125.8)
const todayTrend = ref(5.2)
const monthUsage = ref(3250.5)
const monthTrend = ref(-2.3)
const avgDailyUsage = ref(108.4)
const abnormalCount = ref(3)

// 图表类型
const dailyChartType = ref('line')

// 表格数据
const tableData = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const totalItems = ref(0)

// 格式化数字
const formatNumber = (num) => {
    return num.toLocaleString('en-US', { maximumFractionDigits: 2 })
}

// 获取数据
const fetchData = () => {
    // 这里应该是API调用
    console.log('Fetching data with:', {
        timeRange: timeRange.value,
        customDateRange: customDateRange.value,
        location: selectedLocation.value
    })

    // 模拟数据加载
    setTimeout(() => {
        // 这里只是示例，实际应该从API获取数据
        todayUsage.value = 125.8 + Math.random() * 20
        todayTrend.value = (Math.random() > 0.5 ? 1 : -1) * (Math.random() * 10)
        monthUsage.value = 3250.5 + Math.random() * 100
        monthTrend.value = (Math.random() > 0.5 ? 1 : -1) * (Math.random() * 5)
        avgDailyUsage.value = 108.4 + Math.random() * 5
        abnormalCount.value = Math.floor(Math.random() * 5)

        fetchTableData()
    }, 500)
}

// 获取表格数据
const fetchTableData = () => {
    // 模拟API调用
    const mockData = []
    const locations = ['A区', 'B区', 'C区']
    const statuses = ['正常', '异常']

    for (let i = 0; i < 50; i++) {
        mockData.push({
            date: `2023-05-${String(i % 30 + 1).padStart(2, '0')}`,
            location: locations[i % 3],
            usage: (Math.random() * 200 + 50).toFixed(2),
            cost: (Math.random() * 500 + 100).toFixed(2),
            avgTemperature: (Math.random() * 10 + 20).toFixed(1),
            status: statuses[i % 10 === 0 ? 1 : 0]
        })
    }

    totalItems.value = mockData.length
    tableData.value = mockData.slice(
        (currentPage.value - 1) * pageSize.value,
        currentPage.value * pageSize.value
    )
}

// 导出数据
const exportData = () => {
    console.log('Exporting data...')
    // 实际项目中这里应该调用导出API
}

// 查看详情
const showDetail = (row) => {
    console.log('Showing detail for:', row)
    // 实际项目中这里可以打开详情弹窗或跳转到详情页
}

// 每日用水趋势图表配置
const dailyChartOption = computed(() => {
    const days = []
    const data = []

    for (let i = 6; i >= 0; i--) {
        const date = new Date()
        date.setDate(date.getDate() - i)
        days.push(`${date.getMonth() + 1}/${date.getDate()}`)
        data.push((Math.random() * 200 + 50).toFixed(1))
    }

    return {
        tooltip: {
            trigger: 'axis',
            formatter: '{b}<br/>{a0}: {c0}吨'
        },
        legend: {
            data: ['用水量']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: days
        },
        yAxis: {
            type: 'value',
            name: '用水量(吨)'
        },
        series: [
            {
                name: '用水量',
                type: dailyChartType.value,
                smooth: true,
                data: data,
                itemStyle: {
                    color: '#36a2eb'
                },
                areaStyle: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                        { offset: 0, color: 'rgba(54, 162, 235, 0.5)' },
                        { offset: 1, color: 'rgba(54, 162, 235, 0.1)' }
                    ])
                }
            }
        ]
    }
})

// 用水分布图表配置
const distributionChartOption = computed(() => {
    return {
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c}吨 ({d}%)'
        },
        legend: {
            orient: 'vertical',
            right: 10,
            top: 'center',
            data: ['A区', 'B区', 'C区', 'D区', 'E区']
        },
        series: [
            {
                name: '用水分布',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                itemStyle: {
                    borderRadius: 10,
                    borderColor: '#fff',
                    borderWidth: 2
                },
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: '18',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: [
                    { value: 335, name: 'A区' },
                    { value: 310, name: 'B区' },
                    { value: 234, name: 'C区' },
                    { value: 135, name: 'D区' },
                    { value: 154, name: 'E区' }
                ],
                color: ['#36a2eb', '#4bc0c0', '#ff9f40', '#ff6384', '#9966ff']
            }
        ]
    }
})

// 时段分析图表配置
const hourlyChartOption = computed(() => {
    const hours = []
    const data = []

    for (let i = 0; i < 24; i++) {
        hours.push(`${i}:00`)
        data.push((Math.random() * 30 + (i > 6 && i < 22 ? 50 : 10)).toFixed(1))
    }

    return {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: hours,
            axisLabel: {
                interval: 3
            }
        },
        yAxis: {
            type: 'value',
            name: '用水量(吨)'
        },
        series: [
            {
                name: '用水量',
                type: 'bar',
                data: data,
                itemStyle: {
                    color: function (params) {
                        const colorList = [
                            '#4bc0c0', '#36a2eb', '#ff9f40', '#ff6384', '#9966ff'
                        ]
                        return colorList[params.dataIndex % colorList.length]
                    }
                }
            }
        ]
    }
})

// 初始化加载数据
onMounted(() => {
    fetchData()
})
</script>

<style scoped>
/* 使用Tailwind CSS，不需要额外样式 */
</style>