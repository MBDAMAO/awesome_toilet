<template>
  <div class="w-full h-full flex">
    <!-- 左侧信息栏 -->
    <div class="w-[20%] h-full flex flex-col pr-2 space-y-2 overflow-auto">

      <!-- 厕位占用情况 -->
      <div class=" p-2 rounded-lg shadow">
        <h3 class="text-sm font-bold mb-2">厕位占用情况</h3>
        <div v-for="item in occupy" :key="item.name" class="flex justify-between text-sm mb-1">
          <span>{{ item.name }}</span>
          <span>总位: {{ item.total }}</span>
          <span>空位: {{ item.free }}</span>
        </div>
      </div>

      <!-- 今日客流 -->
      <div class=" p-2 rounded-lg shadow">
        <h3 class="text-sm font-bold mb-2">今日客流</h3>
        <div v-if="today_people.length">
          <div class="flex justify-between text-sm mb-1">
            <span>男卫生间</span>
            <span>{{ today_people[0].man }}</span>
          </div>
          <div class="flex justify-between text-sm mb-1">
            <span>女卫生间</span>
            <span>{{ today_people[0].woman }}</span>
          </div>
          <div class="flex justify-between text-sm mb-1">
            <span>第三卫生间</span>
            <span>{{ today_people[0].third }}</span>
          </div>
        </div>
      </div>

      <!-- 用水量/用电量/用纸量 -->
      <div class=" p-2 rounded-lg shadow">
        <h3 class="text-sm font-bold mb-2">用水量/用电量/用纸量</h3>
        <div v-for="item in resUsage" :key="item.name" class="flex justify-between text-sm mb-1">
          <span>{{ item.name }}</span>
          <span>今日: {{ item.today }}</span>
          <span>本月: {{ item.month }}</span>
        </div>
      </div>

      <!-- 环境监测 -->
      <div class=" p-2 rounded-lg shadow overflow-auto h-[40%]">
        <h3 class="text-sm font-bold mb-2">环境监测</h3>
        <div v-for="item in envData" :key="item.name" class="text-sm mb-1">
          <div class="font-medium">{{ item.name }}</div>
          <div class="pl-2">
            <div>男厕：{{ item.man }}</div>
            <div>女厕：{{ item.woman }}</div>
            <div>第三卫生间：{{ item.third }}</div>
          </div>
        </div>
      </div>

      <!-- 满意度 -->
      <div class=" p-2 rounded-lg shadow h-[10%] flex items-center justify-center">
        <span class="text-sm font-bold">满意度评价</span>
      </div>
    </div>

    <!-- 地图容器 -->
    <div class="h-full w-[80%] relative rounded-lg overflow-hidden bg-transparent"
      style="box-shadow: 0 2px 10px 0 rgba(14, 33, 39, .2);">
      <MapContainer />
    </div>
  </div>
</template>


<script setup lang='ts'>
import MapContainer from "./MapContainer.vue"
import { onMounted, ref } from "vue";
import { getMapPageData } from "@/apis/stat";
const loading = ref(true);
const occupy = ref([

])
const today_people = ref([

])
const resUsage = ref([

])
const envData = ref([

])
onMounted(() => {
  getMapPageData().then((res) => {
    const data = res.data;
    occupy.value = data.occupy;
    today_people.value = [data.today_people];
    resUsage.value = data.res_usage;
    envData.value = data.env_data;
    loading.value = false;
  })

});
</script>

<style scoped>
.transition-all {
  transition-property: all;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 300ms;
}
</style>
