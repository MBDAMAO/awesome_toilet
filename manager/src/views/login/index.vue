<template>
    <div class="h-full w-full bg-slate-600 flex items-center justify-center">
        <div class="h-[400px] w-[600px] bg-slate-400 rounded-xl items-center justify-center flex flex-col p-20">
            <div class="flex justify-between w-full mb-5">
                <el-input v-model="account" placeholder="Account" />
            </div>
            <div class="flex justify-between w-full mb-5">
                <el-input v-model="password" placeholder="Password" />
            </div>
            <div class="flex justify-center w-full mb-5">
                <el-button type="primary" @click="login" class="w-full">Login</el-button>
            </div>
        </div>
    </div>
</template>
<script setup lang='ts'>
import { ref } from 'vue'
import { login as userLogin } from '@/apis/user';
const account = ref('')
const password = ref('')
function login() {
    if (!account.value || !password.value) {
        return
    }
    userLogin(account.value, password.value).then(res => {
        console.log(res)
    })
}
</script>
<style scoped></style>