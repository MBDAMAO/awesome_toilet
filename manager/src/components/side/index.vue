<template>
  <div class="h-full">
    <el-menu
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :collapse="isCollapse"
      style="height: 100%"
      unique-opened
      router
      :default-active="'/root/dashboard'"
    >
      <div
        index="-1"
        @click="isCollapse = !isCollapse"
        class="flex justify-center h-[40px] items-center cursor-pointer"
      >
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
        <!-- <el-menu-item index="/root/user/sub">子账号管理</el-menu-item> -->
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
        <el-menu-item index="/root/alarm_settings">告警管理</el-menu-item>
        <el-menu-item index="/root/msg_push_settings">消息推送</el-menu-item>
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
        <el-menu-item index="/root/people">客流量</el-menu-item>
        <el-menu-item index="/root/water">用水量</el-menu-item>
        <el-menu-item index="/root/elect">用电量</el-menu-item>
        <el-menu-item index="/root/paper">用纸量</el-menu-item>
        <el-menu-item index="/root/alarm">警报统计</el-menu-item>
        <el-menu-item index="/root/satify">满意度统计</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="8">
        <template #title>
          <el-icon>
            <Tools />
          </el-icon>
          <span>工具</span>
        </template>
        <!-- <el-menu-item index="/root/device_test">设备测试</el-menu-item> -->
        <el-menu-item index="/root/editor">厕所户型编辑</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="9">
        <template #title>
          <el-icon>
            <MapLocation />
          </el-icon>
          <span>地图显示</span>
        </template>
        <el-menu-item index="/root/map">地图显示</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>
<script setup lang="ts">
import { Message, Tools, Warning } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()
const isCollapse = ref(false)
const handleOpen = (key: string, keyPath: string[]) => {}
const handleClose = (key: string, keyPath: string[]) => {}
</script>
<style scoped>
::-webkit-scrollbar {
  display: none;
}
</style>
