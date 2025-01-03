import type { Result } from '@/types/result'
import request from '@/utils/request'

export function login(account: string, password: string): Promise<Result<any>> {
  return request({
    url: '/chat/list',
    method: 'post',
    data: {
      account,
      password,
    },
  })
}
