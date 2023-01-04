import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/api/orders/getlist',
    method: 'get',
    params
  })
}

export function updateOrderStatus(params) {
  return request({
    url: '/api/orders/updateOrderStatus',
    method: 'post',
    params
  })
}

export function getOrder(params) {
  return request({
    url: '/api/orders/getOrder',
    method: 'get',
    params
  })
}
