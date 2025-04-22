import type { Result } from '@/types/result'
import request from '@/utils/request'

export function login(account: string, password: string): Promise<Result<unknown>> {
  return request({
    url: '/user/login',
    method: 'post',
    data: {
      account,
      password,
    },
  })
}

export function self(): Promise<Result<unknown>> {
  return request({
    url: '/user/self',
    method: 'get',
  })
}

export function updateSelf(user: object): Promise<Result<unknown>> {
  return request({
    url: '/user/update',
    method: 'post',
    data: user,
  })
}

export async function verifyToken(token: string) {
  const response = await fetch('/api/user/verify', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'token': token,
    },
  });
  if (response.status === 401) {
    return false;
  }
  if (!response.ok) {
    // 处理其他非 2xx 状态码
    return false;
  }

  try {
    const resp = await response.json();
    if (resp.code !== 0) {
      return false;
    }
  } catch (error) {
    console.error('Error parsing response:', error);
    return false;
  }

  return true;
}
