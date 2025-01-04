<template>
    <Container class="w-full h-full">
        <div class="w-full h-full flex">
            <div class="w-[20%] h-full flex flex-col pr-2">
                <div class="h-[10%] w-full pb-2">
                    <Container class="h-full w-full p-1 pl-2">
                        厕位占用情况
                    </Container>
                </div>
                <div class="h-[10%] w-full pb-2">
                    <Container class="h-full w-full p-1 pl-2">
                        今日客流
                    </Container>
                </div>
                <div class="h-[20%] w-full pb-2">
                    <Container class="h-full w-full p-1 pl-2">
                        用水量/用电量/用纸量
                    </Container>
                </div>
                <div class="h-[50%] w-full pb-2">
                    <Container class="h-full w-full p-1 pl-2">
                        环境监测
                    </Container>
                </div>
                <div class="h-[10%] w-full">
                    <Container class="h-full w-full p-1 pl-2">
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
import { onMounted, watch } from "vue";
import AMapLoader from "@amap/amap-jsapi-loader";
window._AMapSecurityConfig = {
    securityJsCode: "0ffd599e5ab5b239d14706e319275e94",
};
const isDark = useDark();
let map = null;
onMounted(() => {
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
            position: [104.100221, 30.675709],
            title: "成华区cesuo",
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
<style scoped></style>