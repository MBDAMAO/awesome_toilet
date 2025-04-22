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

export function addToilet(data: any): Promise<Result<any>> {
  return request({
    url: '/toilet/new',
    method: 'post',
    data,
  })
}

export function updateToilet(data: any): Promise<Result<any>> {
  return request({
    url: '/toilet/update',
    method: 'post',
    data,
  })
}

export function saveToilet(data: any): Promise<Result<any>> {
  return request({
    url: '/toilet/save',
    method: 'post',
    data,
  })
}

export function getToiletInfo(id: string): Promise<Result<any>> {
  return request({
    url: '/toilet/info',
    method: 'get',
    params: { id },
  })
}

export function deleteToilet(id: string): Promise<Result<any>> {
  return request({
    url: '/toilet/delete',
    method: 'get',
    params: { id },
  })
}

export function getAllToiletList(): Promise<Result<any>> {
  return request({
    url: '/toilet/all',
    method: 'get',
  })
}

export function getToiletDetail(id: string): Promise<Result<any>> {
  return request({
    url: '/toilet/detail?id=' + id,
    method: 'get',
  })
}
