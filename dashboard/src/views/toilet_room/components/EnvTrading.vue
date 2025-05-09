<template>
  <div class="env-monitor-container">
    <div ref="chartRef" class="env-chart"></div>
    <div class="env-status">
      <span>当前环境状态:</span>
      <span :class="statusClass">{{ currentStatus }}</span>
      <span class="ml-4">CO₂: {{ latestCO2 }}ppm</span>
      <span class="ml-2">温度: {{ latestTemp }}°C</span>
      <span class="ml-2">湿度: {{ latestHumidity }}%</span>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue'
import * as echarts from 'echarts'

const chartRef = ref(null)
let chartInstance = null

const props = defineProps({
  data: {
    type: Array,
    default: () => [
      { time: '00:00', co2: 420, temperature: 22.5, humidity: 58 },
      { time: '04:00', co2: 480, temperature: 21.8, humidity: 62 },
      { time: '08:00', co2: 520, temperature: 23.2, humidity: 55 },
      { time: '12:00', co2: 580, temperature: 25.7, humidity: 48 },
      { time: '16:00', co2: 620, temperature: 26.3, humidity: 45 },
      { time: '20:00', co2: 550, temperature: 24.1, humidity: 52 },
    ],
  },
})

// 计算最新数据
const latestCO2 = computed(() => props.data[props.data.length - 1].co2)
const latestTemp = computed(() => props.data[props.data.length - 1].temperature)
const latestHumidity = computed(() => props.data[props.data.length - 1].humidity)

const currentStatus = computed(() => {
  // 根据最新数据计算当前状态
  const co2Score =
    latestCO2.value > 1000 ? 100 : latestCO2.value > 800 ? 70 : latestCO2.value > 600 ? 40 : 0
  const tempScore = Math.abs(latestTemp.value - 24) * 10
  const humidityScore = Math.abs(latestHumidity.value - 55) * 0.5
  const totalScore = co2Score + tempScore + humidityScore

  if (totalScore < 30) return '优秀'
  if (totalScore < 60) return '良好'
  if (totalScore < 90) return '一般'
  return '较差'
})

const statusClass = computed(() => {
  return {
    'text-green-500': currentStatus.value === '优秀',
    'text-blue-500': currentStatus.value === '良好',
    'text-yellow-500': currentStatus.value === '一般',
    'text-orange-500': currentStatus.value === '较差',
    'text-red-500': currentStatus.value === '恶劣',
  }
})

const initChart = () => {
  if (!chartRef.value) return

  chartInstance = echarts.init(chartRef.value)

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'cross',
        label: {
          backgroundColor: '#6a7985',
        },
      },
      formatter: (params) => {
        let result = `${params[0].axisValue}<br>`
        params.forEach((item) => {
          let unit = item.seriesName === '温度' ? '°C' : item.seriesName === '湿度' ? '%' : 'ppm'
          result += `${item.marker} ${item.seriesName}: ${item.value}${unit}<br>`
        })
        return result
      },
    },
    legend: {
      data: ['CO₂浓度', '温度', '湿度'],
      top: 10,
      textStyle: {
        color: '#6b7280',
      },
    },
    grid: [
      {
        // CO₂浓度网格
        top: '15%',
        height: '25%',
      },
      {
        // 温度网格
        top: '45%',
        height: '25%',
      },
      {
        // 湿度网格
        top: '75%',
        height: '25%',
      },
    ],
    xAxis: [
      {
        // CO₂浓度X轴
        type: 'category',
        gridIndex: 0,
        data: props.data.map((item) => item.time),
        axisTick: { show: false },
        axisLine: { lineStyle: { color: '#9ca3af' } },
        axisLabel: { color: '#6b7280' },
      },
      {
        // 温度X轴
        type: 'category',
        gridIndex: 1,
        data: props.data.map((item) => item.time),
        axisTick: { show: false },
        axisLine: { lineStyle: { color: '#9ca3af' } },
        axisLabel: { color: '#6b7280', show: false },
      },
      {
        // 湿度X轴
        type: 'category',
        gridIndex: 2,
        data: props.data.map((item) => item.time),
        axisTick: { show: false },
        axisLine: { lineStyle: { color: '#9ca3af' } },
        axisLabel: { color: '#6b7280' },
      },
    ],
    yAxis: [
      {
        // CO₂浓度Y轴
        type: 'value',
        gridIndex: 0,
        name: 'CO₂浓度(ppm)',
        nameTextStyle: { color: '#6b7280' },
        axisLine: { lineStyle: { color: '#8b5cf6' } },
        axisLabel: { color: '#6b7280' },
        splitLine: { lineStyle: { color: '#e5e7eb' } },
        min: 400,
        max: 800,
      },
      {
        // 温度Y轴
        type: 'value',
        gridIndex: 1,
        name: '温度(°C)',
        nameTextStyle: { color: '#6b7280' },
        axisLine: { lineStyle: { color: '#ef4444' } },
        axisLabel: { color: '#6b7280' },
        splitLine: { lineStyle: { color: '#e5e7eb' } },
        min: 20,
        max: 28,
      },
      {
        // 湿度Y轴
        type: 'value',
        gridIndex: 2,
        name: '湿度(%)',
        nameTextStyle: { color: '#6b7280' },
        axisLine: { lineStyle: { color: '#10b981' } },
        axisLabel: { color: '#6b7280' },
        splitLine: { lineStyle: { color: '#e5e7eb' } },
        min: 40,
        max: 70,
      },
    ],
    series: [
      {
        // CO₂浓度系列
        name: 'CO₂浓度',
        type: 'line',
        xAxisIndex: 0,
        yAxisIndex: 0,
        showSymbol: false,
        smooth: true,
        lineStyle: {
          width: 3,
          color: '#8b5cf6',
        },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(139, 92, 246, 0.5)' },
            { offset: 1, color: 'rgba(139, 92, 246, 0.1)' },
          ]),
        },
        data: props.data.map((item) => item.co2),
      },
      {
        // 温度系列
        name: '温度',
        type: 'line',
        xAxisIndex: 1,
        yAxisIndex: 1,
        showSymbol: true,
        symbol: 'circle',
        symbolSize: 8,
        lineStyle: {
          width: 3,
          color: '#ef4444',
        },
        itemStyle: {
          color: '#ef4444',
          borderColor: '#fff',
          borderWidth: 2,
        },
        data: props.data.map((item) => item.temperature),
      },
      {
        // 湿度系列
        name: '湿度',
        type: 'line',
        xAxisIndex: 2,
        yAxisIndex: 2,
        showSymbol: true,
        symbol: 'diamond',
        symbolSize: 10,
        lineStyle: {
          width: 3,
          color: '#10b981',
          type: 'dashed',
        },
        itemStyle: {
          color: '#10b981',
          borderColor: '#fff',
          borderWidth: 2,
        },
        data: props.data.map((item) => item.humidity),
      },
    ],
  }

  chartInstance.setOption(option)
}

onMounted(() => {
  initChart()
  window.addEventListener('resize', () => chartInstance?.resize())
})

watch(
  () => props.data,
  () => {
    if (chartInstance) {
      const xAxisData = props.data.map((item) => item.time)
      chartInstance.setOption({
        xAxis: [{ data: xAxisData }, { data: xAxisData }, { data: xAxisData }],
        series: [
          { data: props.data.map((item) => item.co2) },
          { data: props.data.map((item) => item.temperature) },
          { data: props.data.map((item) => item.humidity) },
        ],
      })
    }
  },
  { deep: true },
)
</script>

<style scoped>
.env-monitor-container {
  @apply bg-white dark:bg-gray-800 rounded-lg shadow p-4 h-[600px];
}
.env-chart {
  @apply w-full h-[540px];
}
.env-status {
  @apply mt-2 text-sm flex items-center text-gray-600 dark:text-gray-300;
}
.env-status span:first-child {
  @apply font-medium;
}
</style>
