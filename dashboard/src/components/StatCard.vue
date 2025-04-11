<template>
  <div class="p-3 rounded-lg" :class="class">
    <div class="flex items-center justify-between">
      <div>
        <p class="text-sm text-gray-500 dark:text-gray-400">{{ title }}</p>
        <p class="text-2xl font-bold mt-1">{{ value }}</p>
      </div>
      <span class="text-3xl">{{ icon }}</span>
    </div>
    <div class="mt-2 flex items-center text-sm">
      <span :class="{
        'text-green-500': trend === 'up',
        'text-red-500': trend === 'down',
        'text-gray-500': trend === 'stable'
      }">
        <svg v-if="trend === 'up'" xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 inline" fill="none"
          viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 10l7-7m0 0l7 7m-7-7v18" />
        </svg>
        <svg v-else-if="trend === 'down'" xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 inline" fill="none"
          viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 14l-7 7m0 0l-7-7m7 7V3" />
        </svg>
        <svg v-else xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 inline" fill="none" viewBox="0 0 24 24"
          stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 12h14" />
        </svg>
        {{ change }}
      </span>
      <span class="text-gray-500 dark:text-gray-400 ml-1">较昨日</span>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps({
  title: String,
  value: [String, Number],
  icon: String,
  trend: {
    type: String,
    validator: (val) => ['up', 'down', 'stable'].includes(val)
  },
  change: String,
  class: String
})
</script>
