import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/api/contacts/list',
    method: 'get',
    params
  })
}
