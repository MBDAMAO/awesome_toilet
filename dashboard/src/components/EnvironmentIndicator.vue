<template>
    <div
        class="flex items-center justify-between p-2 hover:bg-gray-50 dark:hover:bg-gray-700 rounded-lg transition-colors">
        <div class="flex items-center">
            <span class="text-2xl mr-3">{{ icon }}</span>
            <div>
                <div class="font-medium text-gray-700 dark:text-gray-300">{{ title }}</div>
                <div class="text-xs text-gray-500 dark:text-gray-400">{{ getLevelText(level) }}</div>
            </div>
        </div>
        <div class="text-lg font-bold" :class="getValueColor(level)">
            {{ value }}<span class="text-sm font-normal text-gray-500">{{ unit }}</span>
        </div>
    </div>
</template>

<script setup>
defineProps({
    title: String,
    value: [String, Number],
    unit: String,
    level: {
        type: String,
        validator: (val) => ['good', 'normal', 'bad'].includes(val)
    },
    icon: String
})

const getLevelText = (level) => {
    switch (level) {
        case 'good': return '良好'
        case 'normal': return '正常'
        case 'bad': return '较差'
        default: return ''
    }
}

const getValueColor = (level) => {
    switch (level) {
        case 'good': return 'text-green-500'
        case 'normal': return 'text-blue-500'
        case 'bad': return 'text-red-500'
        default: return 'text-gray-500'
    }
}
</script>