<template>
    <Container class="w-full h-full">
        <div class="w-full h-full flex">
            <div class="w-[20%] h-full"></div>
            <div id="map" class="h-full w-[80%] relative rounded-lg overflow-hidden"
                style="box-shadow: 0 2px 10px 0 rgba(14, 33, 39, .2);"></div>
        </div>
    </Container>
</template>
<script setup lang='js'>
import Container from "@/components/container/index.vue";
import AMapLoader from "@amap/amap-jsapi-loader";
window._AMapSecurityConfig = {
    securityJsCode: "0ffd599e5ab5b239d14706e319275e94",
};
AMapLoader.load({
    key: "a4ed2acdb87e1fb1bfce40e716e48ed1", //申请好的 Web 端开发者 Key，首次调用 load 时必填
    version: "2.0", //指定要加载的 JS API 的版本，缺省时默认为 1.4.15
    plugins: ["AMap.Scale"], //需要使用的的插件列表，如比例尺'AMap.Scale'，支持添加多个如：['AMap.Scale','...','...']
})
    .then((AMap) => {
        var map = new AMap.Map("map", { zoom: 16, center: [104.097532, 30.674544] });

        AMap.plugin("AMap.DistrictSearch", function () {
            var district = new AMap.DistrictSearch({
                extensions: "all", //返回行政区边界坐标等具体信息
                level: "district", //设置查询行政区级别为区
            });
            district.search("成华区", function (status, result) {
                console.log(result);
                var bounds = result.districtList[0].boundaries; //获取朝阳区的边界信息
                if (bounds) {
                    for (var i = 0; i < bounds.length; i++) {
                        //生成行政区划 polygon
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
    })
    .catch((e) => {
        console.log(e);
    });
</script>
<style scoped></style>