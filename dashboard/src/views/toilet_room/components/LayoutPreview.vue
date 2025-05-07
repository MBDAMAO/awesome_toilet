<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'

// 接收从父组件传递的布局数据
const props = defineProps({
  layoutData: {
    type: String,
    required: true,
  },
})

// 定义一个固定的 viewBox 大小
const viewBoxWidth = 800
const viewBoxHeight = 600

// 存储最终计算后的元素数据
const processedElements = ref([])

watch(
  () => props.layoutData,
  () => {
    try {
      // 计算布局的最小和最大坐标
      let minX = Number.MAX_VALUE,
        maxX = -Number.MAX_VALUE
      let minY = Number.MAX_VALUE,
        maxY = -Number.MAX_VALUE

      // 解析 layoutData 为 JSON 格式
      const layoutData = JSON.parse(props.layoutData)

      layoutData.forEach((element) => {
        minX = Math.min(minX, element.x)
        maxX = Math.max(maxX, element.x + element.width)
        minY = Math.min(minY, element.y)
        maxY = Math.max(maxY, element.y + element.height)
      })

      // 计算适应的缩放比例
      const scaleX = viewBoxWidth / (maxX - minX)
      const scaleY = viewBoxHeight / (maxY - minY)
      const scale = Math.min(scaleX, scaleY)

      // 计算适应的偏移量，确保元素显示居中
      const offsetX = (viewBoxWidth - (maxX - minX) * scale) / 2
      const offsetY = (viewBoxHeight - (maxY - minY) * scale) / 2

      // 计算所有元素的缩放后的大小和位置
      processedElements.value = layoutData.map((element) => {
        const processedElement = {
          ...element,
          x: (element.x - minX) * scale + offsetX,
          y: (element.y - minY) * scale + offsetY,
          width: element.width * scale,
          height: element.height * scale,
          showRedDot: ['男厕位', '女厕位'].includes(element.type), // 添加标记是否显示红点
        }
        return processedElement
      })
    } catch (error) {
      console.log(error)
    }
  },
)

onMounted(() => {
  console.log(props.layoutData)
})
</script>

<template>
  <div class="w-full h-full relative rounded-lg">
    <!-- 使用 SVG 渲染布局 -->
    <svg
      class="w-full h-full"
      :viewBox="`0 0 ${viewBoxWidth} ${viewBoxHeight}`"
      preserveAspectRatio="xMidYMid meet"
    >
      <!-- 渲染处理后的元素 -->
      <g v-for="(element, index) in processedElements" :key="index">
        <rect
          :x="element.x"
          :y="element.y"
          :width="element.width"
          :height="element.height"
          :fill="element.backgroundColor"
          class="rounded-md"
        />
        <text
          :x="element.x + element.width / 2"
          :y="element.y + element.height / 2"
          :fill="element.fontColor"
          text-anchor="middle"
          alignment-baseline="middle"
          class="font-bold"
        >
          {{ element.type }}
        </text>
        <!-- 如果是厕所位，添加红点 -->
        <circle
          v-if="element.showRedDot"
          :cx="element.x + element.width / 2"
          :cy="element.y + element.height / 2"
          r="10"
          fill="green"
        />
      </g>
    </svg>
  </div>
</template>

<style scoped>
/* 禁止用户交互，保持画布不可拖动 */
div {
  pointer-events: none;
}

/* SVG 文字居中显示 */
text {
  font-size: 16px;
  pointer-events: none;
  /* 禁止与文字的交互 */
}
</style>
