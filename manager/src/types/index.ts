// src/views/device/types.ts
export interface DeviceItem {
  id: number
  name: string
  toilet_name: string
  device_type: string
  toilet_type: string
  pit_type: string
  status: string
  power: number
  description: string
  // 其他字段...
}

export interface DeviceListParams {
  pageNum: number
  pageSize: number
  name?: string
  status?: string
  // 其他查询参数...
}

export interface ToiletItem {
  id: number
  name: string
  location: string
  status: string
  create_time: string
  pits: number
  devices: number
  description: string
}

export interface ToiletListParams {
  page: number
  size: number
  name?: string
  status?: string
}

export interface ToiletListResult {
  list: ToiletItem[]
  total: number
  page: number
  size: number
  pages: number
}
