<template>
  <div class="bg-[#272A48] h-full w-full flex justify-between">
    <div class="flex h-full">
      <div class="h-full w-[50px]">
        <el-icon>
          <Management />
        </el-icon>
      </div>
      <div class="p-2 text-white flex justify-center items-center ml-4">智慧厕所管理系统</div>
    </div>
    <div class="flex h-full text-xs text-white">
      <div class="p-4 flex justify-center items-center mr-4 cursor-pointer" @click="changeTheme()">
        <el-switch v-model="isDark" class="ml-2" style="--el-switch-on-color: #3C3C3C;" />
      </div>
      <div class=" p-2 flex justify-center items-center mr-4 cursor-pointer"
        @click="openNewTab('http://127.0.0.1:18013')">
        前往面板
      </div>
      <div class="p-2 flex justify-center items-center mr-4 cursor-pointer" @click="router.push('/root/user')">
        个人信息</div>
      <div class="p-2 flex justify-center items-center mr-4 cursor-pointer" @click="router.push('/root/toilet')">
        厕所</div>
      <div class="p-2 flex justify-center items-center mr-4 cursor-pointer" @click="router.push('/root/device')">
        设备</div>
      <div class="p-2 flex justify-center items-center mr-4 cursor-pointer" @click="router.push('/root/message')">
        消息</div>
      <div class="h-full w-[50px] p-2 mr-4">
        <div class="h-full w-full rounded-full overflow-hidden">
          <img src="https://damaoz.site/images/avatar.webp" alt="" class="object-contain">
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang='ts'>
import { useRouter } from 'vue-router'
import { useDark } from "@vueuse/core";

const router = useRouter()
const isDark = useDark()
function changeTheme() {
  document.documentElement.setAttribute('class', isDark.value ? 'dark' : 'light');
}
function openNewTab(url: string) {
  window.open(url, '_blank');
}
</script>
<style scoped>
.el-icon {
  height: 50px;
  width: 50px;
  border-radius: 50%;
  color: #fff;
  font-size: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
