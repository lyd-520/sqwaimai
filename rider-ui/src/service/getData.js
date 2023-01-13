import fetch from '../config/fetch'
import {getStore} from '../config/mUtils'

/**
 * 获取首页默认地址
 */

export const cityGuess = () => fetch('/api/cities', {
  type: 'guess'
});


/**
 * 获取首页热门城市
 */

export const hotcity = () => fetch('/api/cities', {
  type: 'hot'
});


/**
 * 获取首页所有城市
 */

export const groupcity = () => fetch('/api/cities', {
  type: 'group'
});


/**
 * 获取当前所在城市
 */

export const currentcity = number => fetch('/api/cities/' + number);


/**
 * 获取搜索地址
 */

export const searchplace = (cityid, value) => fetch('/api/pois', {
  type: 'search',
  city_id: cityid,
  keyword: value
});


/**
 * 获取msite页面地址信息
 */

export const msiteAddress = geohash => fetch('/position/pois', {
  geohash
});


/**
 * 获取图片验证码
 */

export const getcaptchas = () => fetch('/api/captchas/getcaptchas', {}, 'POST');


/**
 * 检测帐号是否存在
 */

export const checkExsis = (checkNumber, type) => fetch('/v1/users/exists', {
  [type]: checkNumber,
  type
});


/**
 * 搜索地址
 */

export const searchNearby = keyword => fetch('/api/pois', {
  type: 'nearby',
  keyword
});


/**
 * 添加地址
 */

export const postAddAddress = (userId, address, address_detail, geohash, name, phone, phone_bk, poi_type, sex, tag, tag_type) => fetch('/api/address/saveUserAddress/' + userId, {
  address,
  address_detail,
  geohash,
  name,
  phone,
  phone_bk,
  poi_type,
  sex,
  tag,
  tag_type,
}, 'POST');



/**
 * 重新发送订单验证码
 */

export const payRequest = (merchantOrderNo, userId) => fetch('/payapi/payment/queryOrder', {
  merchantId: 5,
  merchantOrderNo,
  source: 'MOBILE_WAP',
  userId,
  version: '1.0.0',
});

/**
 * 获取服务中心信息
 */

export const getService = () => fetch('/api/profile/explain');

/**
 * 获取红包
 */

export const getHongbaoNum = id => fetch('/promotion/v2/users/' + id + '/hongbaos?limit=20&offset=0');


/**
 * 获取过期红包
 */


export const getExpired = id => fetch('/promotion/v2/users/' + id + '/expired_hongbaos?limit=20&offset=0');


/**
 * 兑换红包
 */

export const exChangeHongbao = (id, exchange_code, captcha_code) => fetch('/v1/users/' + id + '/hongbao/exchange', {
  exchange_code,
  captcha_code,
}, 'POST');

/**
 * 获取订单列表
 */

export const getOrderList = (user_id,latitude,longitude) => fetch('/api/rider/' + user_id + '/orders', {
  limit: 10,
  offset: 0,
  // sort: 'distance',
  // order: 'desc',
  latitude,
  longitude,
  t: new Date().getTime()
},'POST');

export const finishOrder = (user_id, orderid) => fetch('/api/orders/finishuserorder/' + user_id + '/' + orderid);


/**
 * 获取订单详情
 */

export const getOrderDetail = (orderid) => fetch('/api/orders/getOrderById',{orderid});

export const checkOrder = (userid,orderid) => fetch('/api/orders/ridercheckOrder',{userid,orderid},'POST')

export const sendOrder = (userid,orderid) => fetch('/api/orders/ridersendorder',{userid,orderid},'POST')

/**
 *个人中心里编辑地址
 */

export const getAddressList = (user_id) => fetch('/api/address/queryUserAddress/' + user_id)

export const clearBalance = (userid) => fetch('/api/rider/clearbalance',{userid},'POST')

/**
 *个人中心里搜索地址
 */

export const getSearchAddress = (keyword) => fetch('/api/pois', {
  keyword: keyword,
  type: 'nearby'
})

/**
 * 删除地址
 */

export const deleteAddress = (userid, addressid) => fetch('/api/address/deleteuseraddress/' + userid + '/' + addressid, {}, 'DELETE')

/**
 * 账号密码登录
 */
export const riderLogin = (username, password, captchaCode, captchCodeId) => fetch('/api/rider/login', {username, password, captchaCode, captchCodeId}, 'POST');

export const rideronline = (riderid ) => fetch('/api/rider/online', {riderid,}, 'POST');

export const rideroffline = (riderid) => fetch("/api/rider/offline",{riderid},'POST')

/**
 * 退出登录
 */
export const signout = () => fetch('/api/rider/signout');

/**
 * 改密码
 */
export const changePassword = (username, oldpassWord, newpassword, confirmpassword, captcha_code,captchaId) => fetch('/api/rider/changepassword', {
  username,
  oldpassWord,
  newpassword,
  confirmpassword,
  captcha_code,
  captchaId
}, 'POST');

export const changemobile = (riderid, mobile) =>fetch('/api/rider/'+riderid+'/updatemobile/'+mobile, {riderid,mobile}, 'POST');
