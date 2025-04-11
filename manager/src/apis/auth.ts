import type { Result } from '@/types/result'
import request from '@/utils/request'

export async function handleSSOCallback(token: string) {
  // const userInfo = await ssoVerify(token)

  localStorage.setItem('token', token)
  // localStorage.setItem('userInfo', JSON.stringify(userInfo))
}
