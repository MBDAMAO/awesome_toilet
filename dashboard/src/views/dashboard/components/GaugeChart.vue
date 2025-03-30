<template>
    <div>
        <div class="flex justify-between items-center mb-1">
            <span class="text-sm text-gray-600 dark:text-gray-300">{{ title }}</span>
            <span class="text-sm font-semibold" :class="color">{{ value }}{{ unit }}</span>
        </div>
        <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-2">
            <div class="h-2 rounded-full" :class="color" :style="{ width: `${Math.min(value, 100)}%` }"></div>
        </div>
    </div>
</template>

<script setup>
defineProps({
    title: String,
    value: Number,
    unit: String,
    color: String
})
</script>