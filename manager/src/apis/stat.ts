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
  return Promise.resolve({
    code: 200,
    message: 'success',
    data: {
      envData: [
        {
          name: '温度',
          man: 3222,
          woman: 3222,
          third: 3222,
        },
        {
          name: '湿度',
          man: 3222,
          woman: 3222,
        },
        {
          name: '氨气',
          man: 3222,
          woman: 3222,
        },
        {
          name: '硫化氨',
          man: 3222,
          woman: 3222,
        },
        {
          name: 'PM2.5',
          man: 3222,
          woman: 3222,
        },
        {
          name: 'PM10',
          man: 3222,
          woman: 3222,
        },
        {
          name: '气压',
          man: 3222,
          woman: 3222,
        },
        {
          name: 'CO2',
          man: 3222,
          woman: 3222,
        },
        {
          name: 'TVOC',
          man: 3222,
          woman: 3222,
        },
        {
          name: '水浸状态',
          man: 3222,
          woman: 3222,
        },
        {
          name: '剩余纸量',
          man: 3222,
          woman: 3222,
        },
      ],
      resUsage: [
        {
          name: '用水量',
          today: 3222,
          month: 3222,
        },
        {
          name: '用电量',
          today: 3222,
          month: 3222,
        },
        {
          name: '用纸量',
          today: 3222,
          month: 3222,
        },
      ],
      today_people: [
        {
          man: 100,
          woman: 100,
          third: 100,
        },
      ],
      occupy: [
        {
          name: '男卫生间',
          total: 100,
          free: 100,
        },
        {
          name: '女卫生间',
          total: 100,
          free: 100,
        },
        {
          name: '第三卫生间',
          total: 100,
          free: 100,
        },
      ],
    },
  })
}
