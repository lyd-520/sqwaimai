import request from '@/utils/request'

export function getAddressById(id) {
  return request({
    url: '/api/address/getaddressbyid/'+id,
    method: 'get'
  })
}
