import type { Result } from '@/types/result'
import request from '@/utils/request'

export function getHomeStat(): Promise<Result<any>> {
  return request({
    url: '/data/dashboard',
    method: 'get',
  })
}

export function getMapPageData(): Promise<Result<any>> {
  return request({
    url: '/data/overview',
    method: 'get',
  })
}
