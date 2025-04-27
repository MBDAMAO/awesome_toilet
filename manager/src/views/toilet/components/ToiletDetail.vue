<template>
  <div class="toilet-detail">
    <!-- 加载状态 -->
    <div v-if="loading" class="loading-container">
      <el-icon class="is-loading" color="#409EFC" :size="50">
        <Loading />
      </el-icon>
    </div>

    <!-- 内容区域 -->
    <template v-else>
      <div class="flex flex-row">
        <div class="h-auto w-40 mr-2">
          <MapView :x="location[0]" :y="location[1]" />
        </div>
        <div class="h-auto w-52 mr-2 hover:cursor-pointer" @click="goToEditor">
          <LayoutPreview :layoutData="toilet.design_map" />
        </div>
        <el-descriptions :column="2" border>
          <el-descriptions-item label="厕所ID">{{ toilet.id }}</el-descriptions-item>
          <el-descriptions-item label="厕所名称">{{ toilet.name }}</el-descriptions-item>
          <el-descriptions-item label="位置">{{ toilet.location }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusTagType(toilet.status)">
              {{ statusNameMap[toilet.status] }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="坑位数量">{{ toilet.pits }}</el-descriptions-item>
          <el-descriptions-item label="设备数量">{{ toilet.devices }}</el-descriptions-item>
          <el-descriptions-item label="添加时间">{{ toilet.create_time }}</el-descriptions-item>
          <el-descriptions-item label="备注" :span="2">
            {{ toilet.description || '无' }}
          </el-descriptions-item>
        </el-descriptions>
      </div>

      <div class="mt-6">
        <h3 class="text-lg font-medium mb-3">设备列表</h3>
        <el-table :data="devices" border style="width: 100%">
          <el-table-column prop="id" label="设备ID" width="120" />
          <el-table-column prop="name" label="设备名称" width="150" />
          <el-table-column prop="type" label="设备类型" width="120" />
          <el-table-column prop="description" show-overflow-tooltip label="描述" width="160" />
          <el-table-column prop="status" label="状态" width="100">
            <template #default="scope">
              <el-tag :type="getDeviceStatusTagType(scope.row.status)" size="small">
                {{ statusNameMap[scope.row.status] }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="last_check" label="最后检查" width="180" />
        </el-table>
      </div>

      <div class="mt-6">
        <h3 class="text-lg font-medium mb-3">厕位列表</h3>
        <el-table :data="pits" border style="width: 100%">
          <el-table-column prop="id" label="厕位ID" width="120" />
          <el-table-column prop="name" label="厕位名称" width="150" />
          <el-table-column prop="type" label="厕位类型" width="120" />
          <el-table-column prop="description" label="描述" width="160" />
          <el-table-column prop="status" label="状态" width="100">
            <template #default="scope">
              <el-tag :type="getDeviceStatusTagType(scope.row.status)" size="small">
                {{ statusNameMap[scope.row.status] }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="last_check" label="最后检查" width="180" />
        </el-table>
      </div>
    </template>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { Loading } from '@element-plus/icons-vue'
import type { ToiletItem } from '@/types'
import LayoutPreview from './LayoutPreview.vue'
import MapView from './MapView.vue'
import { getToiletDetail } from '@/apis/toilet'

const props = defineProps<{
  toilet: ToiletItem
}>()

const location = props.toilet.location.split(',').map(Number)
const statusNameMap = {
  '1': '正常',
  '2': '异常',
  '3': '维护中',
  '4': '关闭',
}

function goToEditor() {
  const url = new URL(window.location.href)
  url.pathname = '/root/editor'
  url.searchParams.set('id', props.toilet.id)
  // jump to the editor page
  window.location = url.href
}

const devices = reactive([])
const pits = reactive([])
const toilet = ref({})
const loading = ref(true) // 加载状态控制

const getStatusTagType = (status: string) => {
  const statusMap: Record<string, string> = {
    '1': 'success',
    '2': 'danger',
    '3': 'warning',
    '4': 'info',
  }
  return statusMap[status] || ''
}

const getDeviceStatusTagType = (status: string) => {
  const statusMap: Record<string, string> = {
    '1': 'success',
    '2': 'danger',
    '3': 'warning',
    '4': 'info',
  }
  return statusMap[status] || ''
}

onMounted(() => {
  getToiletDetail(props.toilet.id)
    .then((res) => {
      devices.push(...res.data.devices)
      pits.push(...res.data.pits)
      toilet.value = res.data.toilet
    })
    .finally(() => {
      loading.value = false // 无论成功失败都关闭加载
    })
})
</script>

<style scoped>
.toilet-detail {
  padding: 10px;
  position: relative;
  min-height: 300px;
}

.loading-container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 10;
}

.is-loading {
  animation: rotating 2s linear infinite;
}

@keyframes rotating {
  from {
    transform: rotate(0deg);
  }

  to {
    transform: rotate(360deg);
  }
}
</style>
