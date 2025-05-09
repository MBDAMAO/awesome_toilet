<!-- src/components/CircularProgress.vue -->
<template>
  <div class="flex flex-col items-center justify-center h-full">
    <div class="relative w-32 h-32">
      <svg class="w-full h-full" viewBox="0 0 100 100">
        <!-- 背景圆 -->
        <circle cx="50" cy="50" :r="radius" fill="none" :stroke="bgColor" stroke-width="10" />
        <!-- 进度圆 -->
        <circle
          cx="50"
          cy="50"
          :r="radius"
          fill="none"
          :stroke="progressColor"
          stroke-width="10"
          :stroke-dasharray="circumference"
          :stroke-dashoffset="dashOffset"
          stroke-linecap="round"
          transform="rotate(-90 50 50)"
        />
      </svg>
      <div class="absolute inset-0 flex items-center justify-center">
        <div class="text-center">
          <div class="text-2xl font-bold" :class="color">{{ value }}</div>
          <div class="text-xs text-gray-500 dark:text-gray-400">{{ unit }}</div>
        </div>
      </div>
    </div>
    <div class="mt-2 text-sm font-medium text-gray-700 dark:text-gray-300">{{ title }}</div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps({
  title: { type: String, required: true },
  value: { type: Number, required: true },
  unit: { type: String, required: true },
  color: { type: String, default: 'text-blue-500' },
})

const radius = 40
const circumference = computed(() => 2 * Math.PI * radius)
const dashOffset = computed(() => circumference.value * (1 - props.value / 100))

const bgColor = computed(() => {
  if (props.color.includes('blue')) return '#DBEAFE'
  if (props.color.includes('orange')) return '#FEE2E2'
  if (props.color.includes('green')) return '#D1FAE5'
  return '#E5E7EB'
})

const progressColor = computed(() => {
  if (props.color.includes('blue')) return '#3B82F6'
  if (props.color.includes('orange')) return '#EF4444'
  if (props.color.includes('green')) return '#10B981'
  return '#6B7280'
})
</script>
