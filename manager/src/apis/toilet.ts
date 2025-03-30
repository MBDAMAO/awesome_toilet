import type { Result } from '@/types/result'
import request from '@/utils/request'

export function getToiletList(params: {
  page: number
  size: number
  [key: string]: any
}): Promise<Result<any>> {
  return request({
    url: '/toilet/list',
    method: 'get',
    params,
  })
}

export function deleteToilet(id: string): Promise<Result<any>> {
  return request({
    url: '/toilet/list',
    method: 'get',
  })
}
