<template>
  <div id="map" class="h-full w-full bg-transparent" style="box-shadow: 0 2px 10px 0 rgba(14, 33, 39, .2);"></div>
</template>
<script setup lang='ts'>
import { useDark } from "@vueuse/core";
import { onMounted } from "vue";
import AMapLoader from "@amap/amap-jsapi-loader";
const props = defineProps({
  x: {
    type: Number,
    default: 0,
  },
  y: {
    type: Number,
    default: 0,
  }
})
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
    map = new AMap.Map("map", { zoom: 15, center: [props.x, props.y], mapStyle: style });
    const marker = new AMap.Marker({
      position: [props.x, props.y], // 标记所在的位置
    })
    map.setMapStyle(style);
    map.add(marker);
  }).catch((e) => {
    console.log(e);
  });
});

</script>
<style scoped></style>
