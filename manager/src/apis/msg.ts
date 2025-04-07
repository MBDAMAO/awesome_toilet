import type { Result } from '@/types/result'
import request from '@/utils/request'

export function getMessageList(params: {
    page: number;
    size: number;
    [key: string]: any;
}): Promise<Result<any>> {
    return request({
        url: '/message/list',
        method: 'get',
        params
    })
}