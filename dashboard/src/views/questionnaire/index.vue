<template>
    <div class="min-h-screen bg-gray-50 py-8 px-4 sm:px-6 lg:px-8">
        <div class="max-w-md mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-2xl">
            <!-- 问卷头部 -->
            <div class="p-8">
                <div class="flex items-center justify-center mb-6">
                    <svg class="h-12 w-12 text-indigo-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                </div>
                <h1 class="text-2xl font-bold text-center text-gray-800 mb-2">服务满意度调查</h1>
                <p class="text-center text-gray-600 mb-6">感谢您使用我们的服务，请花几分钟时间填写此问卷</p>

                <!-- 问卷表单 -->
                <form @submit.prevent="submitForm" class="space-y-6">
                    <!-- 问题1 - 总体满意度 -->
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">1. 您对整体服务的满意度如何？</label>
                        <div class="flex justify-between space-x-2">
                            <button v-for="n in 5" :key="n" type="button" @click="setRating('overall', n)" :class="[
                                'flex-1 py-2 rounded-md border',
                                ratings.overall === n
                                    ? 'bg-indigo-100 border-indigo-500 text-indigo-700'
                                    : 'bg-white border-gray-300 text-gray-700 hover:bg-gray-50'
                            ]">
                                {{ n }}
                            </button>
                        </div>
                        <div class="flex justify-between text-xs text-gray-500 mt-1">
                            <span>非常不满意</span>
                            <span>非常满意</span>
                        </div>
                    </div>

                    <!-- 问题2 - 服务质量 -->
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">2. 您对服务质量的评价如何？</label>
                        <div class="flex justify-between space-x-2">
                            <button v-for="n in 5" :key="n" type="button" @click="setRating('quality', n)" :class="[
                                'flex-1 py-2 rounded-md border',
                                ratings.quality === n
                                    ? 'bg-indigo-100 border-indigo-500 text-indigo-700'
                                    : 'bg-white border-gray-300 text-gray-700 hover:bg-gray-50'
                            ]">
                                {{ n }}
                            </button>
                        </div>
                        <div class="flex justify-between text-xs text-gray-500 mt-1">
                            <span>很差</span>
                            <span>很好</span>
                        </div>
                    </div>

                    <!-- 问题3 - 推荐可能性 -->
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">3. 您有多大可能向他人推荐我们的服务？</label>
                        <div class="flex justify-between space-x-2">
                            <button v-for="n in 5" :key="n" type="button" @click="setRating('recommend', n)" :class="[
                                'flex-1 py-2 rounded-md border',
                                ratings.recommend === n
                                    ? 'bg-indigo-100 border-indigo-500 text-indigo-700'
                                    : 'bg-white border-gray-300 text-gray-700 hover:bg-gray-50'
                            ]">
                                {{ n }}
                            </button>
                        </div>
                        <div class="flex justify-between text-xs text-gray-500 mt-1">
                            <span>不可能</span>
                            <span>非常可能</span>
                        </div>
                    </div>

                    <!-- 开放性问题 -->
                    <div>
                        <label for="comments" class="block text-sm font-medium text-gray-700 mb-2">4.
                            您有什么建议或意见想与我们分享吗？</label>
                        <textarea id="comments" v-model="comments" rows="3"
                            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                            placeholder="您的宝贵意见将帮助我们改进服务..."></textarea>
                    </div>

                    <!-- 提交按钮 -->
                    <div>
                        <button type="submit" :disabled="isSubmitting"
                            class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50 disabled:cursor-not-allowed">
                            {{ isSubmitting ? '提交中...' : '提交问卷' }}
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- 提交成功的提示 -->
        <div v-if="isSubmitted" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
            <div class="bg-white rounded-lg p-6 max-w-sm w-full">
                <div class="text-center">
                    <svg class="mx-auto h-12 w-12 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                    </svg>
                    <h3 class="mt-2 text-lg font-medium text-gray-900">提交成功！</h3>
                    <p class="mt-1 text-sm text-gray-500">感谢您的反馈，我们将持续改进服务质量。</p>
                    <div class="mt-4">
                        <button @click="resetForm"
                            class="inline-flex justify-center px-4 py-2 text-sm font-medium text-indigo-900 bg-indigo-100 border border-transparent rounded-md hover:bg-indigo-200 focus:outline-none focus-visible:ring-2 focus-visible:ring-offset-2 focus-visible:ring-indigo-500">
                            关闭
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';

const ratings = ref({
    overall: 0,
    quality: 0,
    recommend: 0
});

const comments = ref('');
const isSubmitting = ref(false);
const isSubmitted = ref(false);

const setRating = (type, value) => {
    ratings.value[type] = ratings.value[type] === value ? 0 : value;
};

const submitForm = () => {
    if (isSubmitting.value) return;

    isSubmitting.value = true;

    // 模拟API请求
    setTimeout(() => {
        console.log('提交的数据:', {
            ratings: ratings.value,
            comments: comments.value
        });

        isSubmitting.value = false;
        isSubmitted.value = true;
    }, 1000);
};

const resetForm = () => {
    ratings.value = {
        overall: 0,
        quality: 0,
        recommend: 0
    };
    comments.value = '';
    isSubmitted.value = false;
};
</script>

<style scoped>
/* 针对移动设备的额外调整 */
@media (max-width: 640px) {
    button[type="button"] {
        padding: 0.5rem 0.25rem;
        font-size: 0.875rem;
    }
}
</style>