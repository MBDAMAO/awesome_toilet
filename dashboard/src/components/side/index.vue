<template>
    <div class="h-full">
        <el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose"
            :collapse="isCollapse" style="height: 100%;">
            <div index="-1" @click="isCollapse = !isCollapse"
                class="flex justify-center h-[40px] items-center cursor-pointer">
                <el-icon>
                    <Switch />
                </el-icon>
            </div>
            <el-menu-item index="1" @click="router.push('/root/dashboard')">
                <el-icon>
                    <HomeFilled />
                </el-icon>
                <span>首页</span>
            </el-menu-item>
            <el-sub-menu index="2">
                <template #title>
                    <el-icon>
                        <ToiletPaper />
                    </el-icon>
                    <span>厕所管理</span>
                </template>
                <el-menu-item index="2-1" @click="router.push('/root/toilet')">厕所管理</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="3">
                <template #title>
                    <el-icon>
                        <User />
                    </el-icon>
                    <span>账号中心</span>
                </template>
                <el-menu-item index="3-1" @click="router.push('/root/user/info')">账号信息</el-menu-item>
                <el-menu-item index="3-2" @click="router.push('/root/user/sub')">子账号管理</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="4">
                <template #title>
                    <el-icon>
                        <Cpu />
                    </el-icon>
                    <span>设备管理</span>
                </template>
                <el-menu-item index="4-1" @click="router.push('/root/device')">设备管理</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="5">
                <template #title>
                    <el-icon>
                        <Warning />
                    </el-icon>
                    <span>告警设置</span>
                </template>
                <el-menu-item index="5-1" @click="router.push('/root/device')">告警管理</el-menu-item>
                <el-menu-item index="5-2" @click="router.push('/root/device')">消息推送</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="6">
                <template #title>
                    <el-icon>
                        <Message />
                    </el-icon>
                    <span>消息</span>
                </template>
                <el-menu-item index="6-1" @click="router.push('/root/message')">消息列表</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="7">
                <template #title>
                    <el-icon>
                        <Tools />
                    </el-icon>
                    <span>工具</span>
                </template>
            </el-sub-menu>
        </el-menu>
    </div>
</template>
<script setup lang='ts'>
import { Message, Tools, Warning } from '@element-plus/icons-vue';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter()
const isCollapse = ref(false)
const handleOpen = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
</script>
<style scoped></style>