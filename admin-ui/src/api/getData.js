import request from '@/utils/request'

/**
 * 获取定位城市
 */

export function cityGuess() {
  return request({
    url: '/api/cities',
    method: 'get',
    params: {
      type: 'guess'
    }
  })
}

/**
 * 获取搜索地址
 */

export function searchplace(cityid, value) {
  return request({
    url: '/api/pois',
    type: 'search',
    params: {
      city_id: cityid,
      keyword: value
    }
  })
}
