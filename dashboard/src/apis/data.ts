// import type { Result } from '@/types/result'
import request from '@/utils/request'

export const getHomeData = () => {
  return request({
    url: '/screen/overview',
    method: 'get',
  })
}

export const getRoomData = (roomId: string) => {
  return request({
    url: `/screen/room?id=${roomId}`,
    method: 'get',
  })
}
