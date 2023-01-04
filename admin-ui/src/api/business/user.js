import request from '@/utils/request'

export function getUserInfo(id) {
  return request({
    url: '/v1/users/getUserById',
    method: 'get',
    params:{user_id:id}
  })
}
