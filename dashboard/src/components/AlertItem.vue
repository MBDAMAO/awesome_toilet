<template>
    <div class="flex items-start p-2 rounded-lg" :class="{
        'bg-red-50 dark:bg-red-900/20': level === 'high',
        'bg-yellow-50 dark:bg-yellow-900/20': level === 'medium',
        'bg-blue-50 dark:bg-blue-900/20': level === 'low'
    }">
        <div class="p-1 rounded-full mr-3 mt-0.5" :class="{
            'bg-red-100 dark:bg-red-800': level === 'high',
            'bg-yellow-100 dark:bg-yellow-800': level === 'medium',
            'bg-blue-100 dark:bg-blue-800': level === 'low'
        }">
            <svg v-if="type === 'water'" xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-blue-500" fill="none"
                viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12" />
            </svg>
            <svg v-else-if="type === 'clean'" xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-green-500"
                fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M3 10h18M7 15h1m4 0h1m-7 4h12a3 3 0 003-3V8a3 3 0 00-3-3H6a3 3 0 00-3 3v8a3 3 0 003 3z" />
            </svg>
            <svg v-else-if="type === 'repair'" xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-yellow-500"
                fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
            </svg>
            <svg v-else xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-gray-500" fill="none" viewBox="0 0 24 24"
                stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
            </svg>
        </div>
        <div class="flex-1">
            <p class="text-sm font-medium">{{ message }}</p>
            <p class="text-xs text-gray-500 dark:text-gray-400 mt-1">{{ time }}</p>
        </div>
    </div>
</template>

<script setup>
defineProps({
    type: String,
    message: String,
    time: String,
    level: {
        type: String,
        validator: (val) => ['high', 'medium', 'low'].includes(val)
    }
})
</script>