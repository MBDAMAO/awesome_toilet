<template>
    <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <div class="bg-white dark:bg-gray-800 rounded-xl shadow-xl w-full max-w-md p-6">
            <div class="flex justify-between items-start mb-4">
                <h3 class="text-xl font-bold text-gray-800 dark:text-white">{{ toilet.name }}</h3>
                <button @click="$emit('close')" class="text-gray-500 hover:text-gray-700 dark:hover:text-gray-300">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24"
                        stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M6 18L18 6M6 6l12 12" />
                    </svg>
                </button>
            </div>

            <div class="space-y-4">
                <div class="flex items-center">
                    <div class="w-1/3 text-gray-600 dark:text-gray-300">状态</div>
                    <div class="flex-1">
                        <span :class="{
                            'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200': toilet.status === 'busy',
                            'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200': toilet.status === 'free',
                            'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200': toilet.status === 'repair',
                            'bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200': toilet.status === 'normal'
                        }" class="px-2 py-1 rounded-full text-sm">
                            {{ getStatusText(toilet.status) }}
                        </span>
                    </div>
                </div>

                <div class="flex items-center">
                    <div class="w-1/3 text-gray-600 dark:text-gray-300">今日使用</div>
                    <div class="flex-1 font-medium">187人次</div>
                </div>

                <div class="flex items-center">
                    <div class="w-1/3 text-gray-600 dark:text-gray-300">清洁时间</div>
                    <div class="flex-1">2小时前</div>
                </div>

                <div class="flex items-center">
                    <div class="w-1/3 text-gray-600 dark:text-gray-300">设施情况</div>
                    <div class="flex-1">
                        <div class="flex flex-wrap gap-2">
                            <span
                                class="inline-flex items-center px-2 py-1 rounded-full text-xs bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200">
                                <svg xmlns="http://www.w3.org/2000/svg" class="h-3 w-3 mr-1" fill="none"
                                    viewBox="0 0 24 24" stroke="currentColor">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M5 13l4 4L19 7" />
                                </svg>
                                纸巾充足
                            </span>
                            <span
                                class="inline-flex items-center px-2 py-1 rounded-full text-xs bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200">
                                <svg xmlns="http://www.w3.org/2000/svg" class="h-3 w-3 mr-1" fill="none"
                                    viewBox="0 0 24 24" stroke="currentColor">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M5 13l4 4L19 7" />
                                </svg>
                                洗手液充足
                            </span>
                            <span
                                class="inline-flex items-center px-2 py-1 rounded-full text-xs bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200">
                                <svg xmlns="http://www.w3.org/2000/svg" class="h-3 w-3 mr-1" fill="none"
                                    viewBox="0 0 24 24" stroke="currentColor">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M6 18L18 6M6 6l12 12" />
                                </svg>
                                2号位维修中
                            </span>
                        </div>
                    </div>
                </div>

                <div class="flex items-center">
                    <div class="w-1/3 text-gray-600 dark:text-gray-300">环境监测</div>
                    <div class="flex-1">
                        <div class="grid grid-cols-3 gap-2">
                            <div class="text-center">
                                <div class="text-lg font-bold text-green-500">85</div>
                                <div class="text-xs text-gray-500 dark:text-gray-400">AQI</div>
                            </div>
                            <div class="text-center">
                                <div class="text-lg font-bold text-orange-500">26°C</div>
                                <div class="text-xs text-gray-500 dark:text-gray-400">温度</div>
                            </div>
                            <div class="text-center">
                                <div class="text-lg font-bold text-blue-500">65%</div>
                                <div class="text-xs text-gray-500 dark:text-gray-400">湿度</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="mt-6 pt-4 border-t border-gray-200 dark:border-gray-700 flex justify-between">
                <button @click="gotoRoom()"
                    class="px-4 py-2 bg-gray-100 dark:bg-gray-700 rounded-lg text-gray-700 dark:text-gray-200 hover:bg-gray-200 dark:hover:bg-gray-600 transition-colors">
                    导航前往
                </button>
                <button class="px-4 py-2 bg-blue-500 rounded-lg text-white hover:bg-blue-600 transition-colors">
                    报修/反馈
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
const props = defineProps({
    toilet: Object
})

const emit = defineEmits(['close'])
const gotoRoom = () => {
    // 路由到/room
    window.location.href = `/page/room?id=${props.toilet.id}`
}
const getStatusText = (status) => {
    switch (status) {
        case 'busy': return '使用高峰'
        case 'free': return '空闲中'
        case 'repair': return '维修中'
        default: return '正常使用'
    }
}
</script>