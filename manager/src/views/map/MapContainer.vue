<template>
  <div id="map" class="h-full w-full bg-transparent" style="box-shadow: 0 2px 10px 0 rgba(14, 33, 39, .2);"></div>
</template>
<script setup lang='ts'>
import { useDark } from "@vueuse/core";
import { onMounted, watch } from "vue";
import AMapLoader from "@amap/amap-jsapi-loader";
import { getAllToiletList } from "@/apis/toilet";

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
      const district = new AMap.DistrictSearch({
        extensions: "all",
        level: "district",
      });
      district.search("成华区", function (status, result) {
        const bounds = result.districtList[0].boundaries;
        if (bounds) {
          for (let i = 0; i < bounds.length; i++) {
            const polygon = new AMap.Polygon({
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
    // marker.on("click", onMarkerClick); //绑定click事件
    watch(isDark, (newValue) => {
      console.log(newValue);
      if (newValue) {
        map.setMapStyle('amap://styles/darkblue');
      } else {
        map.setMapStyle('amap://styles/normal');
      }
    });
    getAllToiletList().then((res) => {
      console.log(res.data);
      for (let i = 0; i < res.data.length; i++) {
        const toilet = res.data[i];
        // 解析 location 字符串为经纬度数组
        const locationArray = toilet.location.split(',').map(Number);
        const marker = new AMap.Marker({
          position: locationArray, // 使用解析后的经纬度数组
          title: toilet.name,
        });
        map.add(marker);
      }
    });
  }).catch((e) => {
    console.log(e);
  });
});

</script>
<style scoped></style>
