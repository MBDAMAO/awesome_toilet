<template>
  <div class="flex items-center p-2 hover:bg-gray-100 dark:hover:bg-gray-700 rounded-lg transition-colors">
    <div class="w-6 h-6 flex items-center justify-center rounded-full mr-3" :class="{
      'bg-yellow-500 text-white': rank === 1,
      'bg-gray-300 dark:bg-gray-600 text-gray-800 dark:text-white': rank > 1
    }">
      {{ rank }}
    </div>
    <div class="flex-1 truncate">
      {{ name }}
    </div>
    <div class="text-sm text-gray-500 dark:text-gray-400">
      {{ count }}次
    </div>
    <div class="ml-2">
      <svg v-if="trend === 'up'" xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 text-green-500" fill="none"
        viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 10l7-7m0 0l7 7m-7-7v18" />
      </svg>
      <svg v-else-if="trend === 'down'" xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 text-red-500" fill="none"
        viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 14l-7 7m0 0l-7-7m7 7V3" />
      </svg>
      <svg v-else xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 text-gray-500" fill="none" viewBox="0 0 24 24"
        stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 12h14" />
      </svg>
    </div>
  </div>
</template>
<script setup lang="ts">
defineProps({
  rank: Number,
  name: String,
  count: Number,
  trend: {
    type: String,
    validator: (val) => ['up', 'down', 'stable'].includes(val)
  }
})
</script>
