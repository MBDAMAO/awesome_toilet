<template>
    <div class="h-full">
        <el-menu class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="isCollapse"
            style="height: 100%;" unique-opened router :default-active="'/root/dashboard'">
            <div index="-1" @click="isCollapse = !isCollapse"
                class="flex justify-center h-[40px] items-center cursor-pointer">
                <el-icon>
                    <Switch />
                </el-icon>
            </div>
            <el-menu-item index="/root/dashboard">
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
                <el-menu-item index="/root/toilet">厕所管理</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="3">
                <template #title>
                    <el-icon>
                        <User />
                    </el-icon>
                    <span>账号中心</span>
                </template>
                <el-menu-item index="/root/user/info">账号信息</el-menu-item>
                <el-menu-item index="/root/user/sub">子账号管理</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="4">
                <template #title>
                    <el-icon>
                        <Cpu />
                    </el-icon>
                    <span>设备管理</span>
                </template>
                <el-menu-item index="/root/device">设备管理</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="5">
                <template #title>
                    <el-icon>
                        <Warning />
                    </el-icon>
                    <span>告警设置</span>
                </template>
                <el-menu-item index="/root/device">告警管理</el-menu-item>
                <el-menu-item index="/root/device">消息推送</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="6">
                <template #title>
                    <el-icon>
                        <Message />
                    </el-icon>
                    <span>消息</span>
                </template>
                <el-menu-item index="/root/message">消息列表</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="7">
                <template #title>
                    <el-icon>
                        <DataAnalysis />
                    </el-icon>
                    <span>数据统计</span>
                </template>
                <el-menu-item index="7-1" @click="router.push('/root/message')">客流量</el-menu-item>
                <el-menu-item index="7-2" @click="router.push('/root/message')">用水量</el-menu-item>
                <el-menu-item index="7-3" @click="router.push('/root/message')">用电量</el-menu-item>
                <el-menu-item index="7-4" @click="router.push('/root/message')">用纸量</el-menu-item>
                <el-menu-item index="7-5" @click="router.push('/root/message')">警报统计</el-menu-item>
                <el-menu-item index="7-6" @click="router.push('/root/message')">满意度统计</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="8">
                <template #title>
                    <el-icon>
                        <Tools />
                    </el-icon>
                    <span>工具</span>
                </template>
                <el-menu-item index="8-1" @click="router.push('/root/message')">设备测试</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="9">
                <template #title>
                    <el-icon>
                        <MapLocation />
                    </el-icon>
                    <span>地图显示</span>
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