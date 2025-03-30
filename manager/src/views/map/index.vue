<template>
    <Container class="w-full h-full">
        <div class="w-full h-full flex">
            <div class="w-[20%] h-full flex flex-col pr-2">
                <div class="flex w-full pb-2">
                    <Container class="h-full w-full pt-1 pl-2 pb-2 pr-2">
                        厕位占用情况
                        <el-table :data="occupy" border style="width: 100%" v-loading="loading">
                            <el-table-column prop="name" label="" />
                            <el-table-column prop="total" label="总位" />
                            <el-table-column prop="free" label="空位" />
                        </el-table>
                    </Container>
                </div>
                <div class="flex w-full pb-2">
                    <Container class="h-full w-full pt-1 pl-2 pb-2 pr-2">
                        今日客流
                        <el-table :data="today_people" border style="width: 100%" v-loading="loading">
                            <el-table-column prop="man" label="男卫生间" />
                            <el-table-column prop="woman" label="女卫生间" />
                            <el-table-column prop="third" label="第三卫生间" />
                        </el-table>
                    </Container>
                </div>
                <div class="flex w-full pb-2">
                    <Container class="h-full w-full pt-1 pl-2 pb-2 pr-2">
                        用水量/用电量/用纸量
                        <el-table :data="resUsage" border style="width: 100%" v-loading="loading" cc>
                            <el-table-column prop="name" label="" />
                            <el-table-column prop="today" label="今日" />
                            <el-table-column prop="month" label="本月" />
                        </el-table>
                    </Container>
                </div>
                <div class="h-[40%] w-full pb-2 ">
                    <Container class="h-full w-full pt-1 pl-2 pb-2 pr-2 overflow-auto">
                        环境监测
                        <el-table :data="envData" border style="width: 100%" v-loading="loading">
                            <el-table-column prop="name" label="" />
                            <el-table-column prop="man" label="男厕" />
                            <el-table-column prop="woman" label="女厕" />
                            <el-table-column prop="third" label="第三卫生间" />
                        </el-table>
                    </Container>
                </div>
                <div class="h-[10%] w-full">
                    <Container class="h-full w-full pt-1 pl-2">
                        满意度评价
                    </Container>
                </div>
            </div>
            <div id="map" class="h-full w-[80%] relative rounded-lg overflow-hidden bg-transparent"
                style="box-shadow: 0 2px 10px 0 rgba(14, 33, 39, .2);"></div>
        </div>
    </Container>
</template>
<script setup lang='js'>
import { useDark } from "@vueuse/core";
import Container from "@/components/container/index.vue";
import { onMounted, ref, watch } from "vue";
import AMapLoader from "@amap/amap-jsapi-loader";
import { getMapPageData } from "@/apis/stat";
window._AMapSecurityConfig = {
    securityJsCode: "0ffd599e5ab5b239d14706e319275e94",
};
const isDark = useDark();
const loading = ref(true);
let map = null;
let occupy = ref([

])
let today_people = ref([

])
let resUsage = ref([

])
let envData = ref([

])
onMounted(() => {
    getMapPageData().then((res) => {
        let data = res.data;
        occupy.value = data.occupy;
        today_people.value = [data.today_people];
        resUsage.value = data.res_usage;
        envData.value = data.env_data;
        loading.value = false;
    })
    AMapLoader.load({
        key: "a4ed2acdb87e1fb1bfce40e716e48ed1",
        version: "2.0",
        plugins: ["AMap.Scale"],
    }).then((AMap) => {
        let style = null;
        if (isDark.value) {
            style = 'amap://styles/darkblue'
        } else {
            style = 'amap://styles/normal'
        }
        map = new AMap.Map("map", { zoom: 16, center: [104.097532, 30.674544], mapStyle: style });
        AMap.plugin("AMap.DistrictSearch", function () {
            var district = new AMap.DistrictSearch({
                extensions: "all",
                level: "district",
            });
            district.search("成华区", function (status, result) {
                var bounds = result.districtList[0].boundaries;
                if (bounds) {
                    for (var i = 0; i < bounds.length; i++) {
                        var polygon = new AMap.Polygon({
                            map: map, //显示该覆盖物的地图对象
                            strokeWeight: 1, //轮廓线宽度
                            path: bounds[i], //多边形轮廓线的节点坐标数组
                            fillOpacity: 0.7, //多边形填充透明度
                            fillColor: "#CCF3FF", //多边形填充颜色
                            strokeColor: "#CC66CC", //线条颜色
                        });
                    }
                    map.setFitView(); //将覆盖物调整到合适视野
                }
            });
        });
        const marker = new AMap.Marker({
            position: [104.095432, 30.672345],
            title: "成华区cesuo1",
        });
        map.add(marker);
        // marker.on("click", onMarkerClick); //绑定click事件
        watch(isDark, (newValue) => {
            console.log(newValue);
            if (newValue) {
                map.setMapStyle('amap://styles/darkblue');
            } else {
                map.setMapStyle('amap://styles/normal');
            }
        });
    }).catch((e) => {
        console.log(e);
    });
});
</script>
<style scoped>
.transition-all {
    transition-property: all;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
    transition-duration: 300ms;
}
</style>