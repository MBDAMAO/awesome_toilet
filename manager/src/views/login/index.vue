<template>
    <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-50 to-indigo-100 p-4">
        <div class="w-full max-w-md transform transition-all duration-300 hover:scale-[1.01]">
            <div class="bg-white shadow-xl rounded-xl overflow-hidden">
                <!-- Header with gradient background -->
                <div class="bg-gradient-to-r from-blue-500 to-indigo-600 p-6">
                    <h2 class="text-2xl font-bold text-white text-center">Welcome Back</h2>
                    <p class="text-blue-100 text-center mt-1">Sign in to your account</p>
                </div>

                <!-- Form content -->
                <div class="p-8">
                    <form @submit.prevent="handleSubmit" class="space-y-6">
                        <div class="mb-6">
                            <label class="text-gray-600 font-medium block mb-1">Account</label>
                            <div class="relative">
                                <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                                    <i class="fas fa-user"></i>
                                </span>
                                <input v-model="form.account" type="text" placeholder="Enter your account"
                                    class="w-full pl-10 p-3 border rounded-lg shadow-sm focus:ring-2 focus:ring-blue-400">
                            </div>
                        </div>

                        <div class="mb-6">
                            <label class="text-gray-600 font-medium block mb-1">Password</label>
                            <div class="relative">
                                <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
                                    <i class="fas fa-lock"></i>
                                </span>
                                <input v-model="form.password" type="password" placeholder="Enter your password"
                                    class="w-full pl-10 p-3 border rounded-lg shadow-sm focus:ring-2 focus:ring-blue-400">
                            </div>
                        </div>

                        <div class="flex items-center justify-between mb-6">
                            <label class="inline-flex items-center text-gray-600">
                                <input v-model="rememberMe" type="checkbox" class="form-checkbox text-blue-600">
                                <span class="ml-2">Remember me</span>
                            </label>
                        </div>

                        <button type="submit"
                            class="w-full h-12 rounded-lg shadow-md bg-gradient-to-r from-blue-500 to-indigo-600 text-white text-lg font-medium hover:from-blue-600 hover:to-indigo-700 transition-all duration-300">
                            Sign In
                        </button>

                        <div v-if="errorMessage" class="text-red-500 text-center py-3 px-4 bg-red-50 rounded-lg mt-4">
                            <i class="fas fa-exclamation-circle mr-2"></i>{{ errorMessage }}
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { login as userLogin } from '@/apis/user';

const router = useRouter();
const loading = ref(false);
const errorMessage = ref('');
const rememberMe = ref(false);

const form = reactive({
    account: '',
    password: ''
});

const handleSubmit = async () => {
    errorMessage.value = '';

    if (!form.account || form.account.length < 3 || form.account.length > 20) {
        errorMessage.value = 'Account length should be between 3 and 20 characters';
        return;
    }

    if (!form.password || form.password.length < 6 || form.password.length > 20) {
        errorMessage.value = 'Password length should be between 6 and 20 characters';
        return;
    }

    loading.value = true;

    try {
        const res = await userLogin(form.account, form.password);

        if (res.code === 0) {
            localStorage.setItem('token', res.data.token);
            if (rememberMe.value) {
                localStorage.setItem('rememberedAccount', form.account);
            }
            router.push('/root/dashboard');
        } else {
            errorMessage.value = res.message || 'Login failed';
        }
    } catch (error) {
        errorMessage.value = 'Server error, please try again later';
    } finally {
        loading.value = false;
    }
};
</script>

<style scoped>
input:focus {
    outline: none;
}
</style>