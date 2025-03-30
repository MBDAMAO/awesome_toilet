import type { Result } from '@/types/result'
import request from '@/utils/request'

export function getDeviceList(params: {
  page: number;
  size: number;
  [key: string]: any;
}): Promise<Result<any>> {
  return request({
    url: '/device/list',
    method: 'get',
    params
  })
}