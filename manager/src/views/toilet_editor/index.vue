<script setup lang="ts">
import { onMounted, ref } from 'vue'
import Preview from './preview.vue'
import { getAllToiletList, getToiletDesignMapById, updateToilet } from '@/apis/toilet'
import { ElMessage } from 'element-plus'

const gridSize = 20
const elements = ref([])
const selectedElement = ref(null)
const offset = ref({ x: 0, y: 0 })
const draggingElement = ref(null)
const resizingElement = ref(null)
const initialSize = ref({ width: 0, height: 0 })
const startPosition = ref({ x: 0, y: 0 })
const allToilets = ref([])
const value = ref()

onMounted(() => {
  const urlParams = new URLSearchParams(window.location.search)
  const id = urlParams.get('id')

  if (id) {
    getToiletDesignMapById(id).then((response) => {
      const layoutData = response.data.design_map
      elements.value = JSON.parse(layoutData)
    })
  }
  getAllToiletList().then((response) => {
    const toilets = response.data
    allToilets.value.push(...toilets)
    if (id) {
      value.value = Number(id)
    }
  })
})

const componentTypes = [
  { type: '男厕所', width: 100, height: 120, backgroundColor: '#4B8CFF', fontColor: 'white' },
  { type: '男厕位', width: 60, height: 80, backgroundColor: '#9CA3AF', fontColor: 'white' },
  { type: '女厕所', width: 100, height: 120, backgroundColor: '#FF75B3', fontColor: 'white' },
  { type: '女厕位', width: 60, height: 80, backgroundColor: '#9CA3AF', fontColor: 'white' },
  { type: '洗手台', width: 80, height: 50, backgroundColor: '#FBBF24', fontColor: 'white' },
  { type: '入口', width: 80, height: 50, backgroundColor: '#81C784', fontColor: 'white' },
  { type: '无障碍厕所', width: 120, height: 140, backgroundColor: '#34D399', fontColor: 'white' }, // 无障碍元素
]

// 选择元素
const selectElement = (event, element) => {
  selectedElement.value = element
  offset.value = {
    x: event.clientX - element.x,
    y: event.clientY - element.y,
  }
}

// 更新元素位置
const updatePosition = (event) => {
  if (selectedElement.value) {
    selectedElement.value.x = Math.round((event.clientX - offset.value.x) / gridSize) * gridSize
    selectedElement.value.y = Math.round((event.clientY - offset.value.y) / gridSize) * gridSize
  }
}
const show_preview = ref(false)
const preview = () => {
  layoutData.value = elements.value.map((element) => ({
    type: element.type,
    x: element.x,
    y: element.y,
    width: element.width,
    height: element.height,
    backgroundColor: element.backgroundColor, // 确保包含 backgroundColor 属性
    fontColor: element.fontColor, // 确保包含 fontColor 属性
  }))
  show_preview.value = true
}
// 取消选择元素
const deselectElement = () => {
  selectedElement.value = null
  resizingElement.value = null
}

// 开始拖动
const startDrag = (event, component) => {
  draggingElement.value = { ...component, x: event.clientX, y: event.clientY, id: Date.now() }
}

// 放置元素
const dropElement = (event) => {
  if (draggingElement.value) {
    // 获取画布相对于视口的位置
    const canvasRect = event.target.getBoundingClientRect()

    // 计算元素放置的网格位置（相对于画布）
    const x = Math.round((event.clientX - canvasRect.left) / gridSize) * gridSize
    const y = Math.round((event.clientY - canvasRect.top) / gridSize) * gridSize

    // 将元素放到正确的位置
    elements.value.push({
      ...draggingElement.value,
      x: x,
      y: y,
    })
    draggingElement.value = null
  }
}
const layoutData = ref([]) // 用于存储布局数据的响应式变量

// 开始调整大小
const startResize = (event, element) => {
  resizingElement.value = element
  initialSize.value = { width: element.width, height: element.height }
  startPosition.value = { x: event.clientX, y: event.clientY }
  event.stopPropagation()
}

// 更新元素大小
const updateSize = (event) => {
  if (resizingElement.value) {
    const deltaX = event.clientX - startPosition.value.x
    const deltaY = event.clientY - startPosition.value.y
    resizingElement.value.width = Math.max(
      gridSize,
      Math.round((initialSize.value.width + deltaX) / gridSize) * gridSize,
    )
    resizingElement.value.height = Math.max(
      gridSize,
      Math.round((initialSize.value.height + deltaY) / gridSize) * gridSize,
    )
  }
}

// 上传户型数据
const uploadLayout = async () => {
  const layoutData = elements.value.map((element) => ({
    type: element.type,
    x: element.x,
    y: element.y,
    width: element.width,
    height: element.height,
    backgroundColor: element.backgroundColor, // 确保包含 backgroundColor 属性
    fontColor: element.fontColor, // 确保包含 fontColor 属性
  }))
  console.log('Uploading layout:', JSON.stringify(layoutData))
  const id = new URLSearchParams(window.location.search).get('id')
  if (id) {
    await updateToilet({ id, design_map: JSON.stringify(layoutData) })
    ElMessage({
      type: 'success',
      message: '户型数据上传成功！',
    })
  } else {
    ElMessage({
      type: 'error',
      message: '户型数据上传失败！',
    })
  }
}

function handleSelect(value) {
  const selectedToilet = allToilets.value.find((toilet) => toilet.id === value)
  if (selectedToilet) {
    getToiletDesignMapById(selectedToilet.id).then((response) => {
      const layoutData = response.data.design_map
      elements.value = JSON.parse(layoutData)
      ElMessage({
        type: 'success',
        message: '户型数据加载成功！' + selectedToilet.name,
      })
    })
  }
}
</script>

<template>
  <div class="w-full h-full flex" @mousemove="updatePosition" @mouseup="deselectElement">
    <!-- 左侧组件栏 -->
    <div class="w-1/5 h-full p-4 rounded-lg shadow-lg">
      <h2 class="text-lg font-bold mb-2">选择厕所以编辑户型</h2>
      <el-select
        class="w-full mb-4"
        v-model="value"
        placeholder="Select"
        size="large"
        @change="handleSelect(value)"
      >
        <el-option v-for="item in allToilets" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <h2 class="text-lg font-bold mb-2">组件</h2>
      <div
        v-for="component in componentTypes"
        :key="component.type"
        class="p-3 mb-3 cursor-grab rounded-lg shadow-md hover:bg-opacity-90 transition-all"
        :style="{ backgroundColor: component.backgroundColor, color: component.fontColor }"
        draggable="true"
        @dragstart="(event) => startDrag(event, component)"
      >
        {{ component.type }}
      </div>
    </div>

    <!-- 右侧编辑区域 -->
    <div
      class="border border-gray-300 w-4/5 h-full relative rounded-lg shadow-lg"
      @drop="dropElement"
      @dragover.prevent
      @mousemove="updateSize"
    >
      <div
        class="absolute w-full h-full"
        style="
          background-size: 20px 20px;
          background-image: linear-gradient(
              to right,
              rgba(200, 200, 200, 0.5) 1px,
              transparent 1px
            ),
            linear-gradient(to bottom, rgba(200, 200, 200, 0.5) 1px, transparent 1px);
        "
      ></div>
      <div
        v-for="element in elements"
        :key="element.id"
        :style="{
          left: element.x + 'px',
          top: element.y + 'px',
          width: element.width + 'px',
          height: element.height + 'px',
          backgroundColor: element.backgroundColor,
          color: element.fontColor,
        }"
        class="absolute cursor-pointer flex items-center justify-center rounded-lg shadow-md"
        @mousedown="(event) => selectElement(event, element)"
      >
        {{ element.type }}
        <div
          class="absolute w-3 h-3 bg-red-500 bottom-0 right-0 cursor-se-resize"
          @mousedown="(event) => startResize(event, element)"
        ></div>
      </div>
    </div>
  </div>

  <!-- 上传按钮 -->
  <div class="fixed bottom-4 right-4">
    <button
      @click="uploadLayout"
      class="bg-green-500 text-white p-3 rounded-lg shadow-lg hover:bg-green-600 transition-all"
    >
      上传户型
    </button>
  </div>
  <div class="fixed bottom-4 right-40">
    <button
      @click="preview"
      class="bg-green-500 text-white p-3 rounded-lg shadow-lg hover:bg-green-600 transition-all"
    >
      preview
    </button>
  </div>
  <div class="absolute top-2 left-2">
    <Preview :layoutData="layoutData" v-if="show_preview"></Preview>
  </div>
</template>

<style scoped>
.absolute {
  position: absolute;
  user-select: none;
}
</style>
