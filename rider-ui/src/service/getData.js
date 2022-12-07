import fetch from '../config/fetch'
import {getStore} from '../config/mUtils'

/**
 * 获取首页默认地址
 */

export const cityGuess = () => fetch('/v1/cities', {
  type: 'guess'
});


/**
 * 获取首页热门城市
 */

export const hotcity = () => fetch('/v1/cities', {
  type: 'hot'
});


/**
 * 获取首页所有城市
 */

export const groupcity = () => fetch('/v1/cities', {
  type: 'group'
});


/**
 * 获取当前所在城市
 */

export const currentcity = number => fetch('/v1/cities/' + number);


/**
 * 获取搜索地址
 */

export const searchplace = (cityid, value) => fetch('/v1/pois', {
  type: 'search',
  city_id: cityid,
  keyword: value
});


/**
 * 获取msite页面地址信息
 */

export const msiteAddress = geohash => fetch('/v1/position/pois', {
  geohash
});


/**
 * 获取msite商铺列表
 */

export const shopList = (latitude, longitude, offset, restaurant_category_id = '', restaurant_category_ids = '', order_by = '', delivery_mode = '', support_ids = []) => {
  let supportStr = '';
  support_ids.forEach(item => {
    if (item.status) {
      supportStr += '&support_ids[]=' + item.id;
    }
  });
  let data = {
    latitude,
    longitude,
    offset,
    limit: '20',
    'extras': 'activities',
    keyword: '',
    restaurant_category_id,
    'restaurant_category_ids': restaurant_category_ids,
    order_by,
    'delivery_mode': delivery_mode + supportStr
  };
  return fetch('/shopping/restaurants', data);
};

/**
 * 获取短信验证码
 */

export const mobileCode = phone => fetch('/v4/mobile/verify_code/send', {
  mobile: phone,
  scene: 'login',
  type: 'sms'
}, 'POST');


/**
 * 获取图片验证码
 */

export const getcaptchas = () => fetch('/v1/captchas', {}, 'POST');


/**
 * 检测帐号是否存在
 */

export const checkExsis = (checkNumber, type) => fetch('/v1/users/exists', {
  [type]: checkNumber,
  type
});


/**
 * 发送帐号
 */

export const sendMobile = (sendData, captcha_code, type, password) => fetch('/v1/mobile/verify_code/send', {
  action: "send",
  captcha_code,
  [type]: sendData,
  type: "sms",
  way: type,
  password,
}, 'POST');

/**
 * 获取地址列表
 */

export const getAddress = (id, sig) => fetch('/v1/carts/' + id + '/addresses', {
  sig
});


/**
 * 搜索地址
 */

export const searchNearby = keyword => fetch('/v1/pois', {
  type: 'nearby',
  keyword
});


/**
 * 添加地址
 */

export const postAddAddress = (userId, address, address_detail, geohash, name, phone, phone_bk, poi_type, sex, tag, tag_type) => fetch('/v1/users/' + userId + '/addresses', {
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

export const getService = () => fetch('/v3/profile/explain');

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
 * 获取用户信息
 */

export const getUser = () => fetch('/v1/users', {user_id: getStore('user_id')});

/**
 * 获取订单列表
 */

export const getOrderList = (user_id,latitude,longitude) => fetch('/rider/' + user_id + '/orders', {
  limit: 10,
  offset: 0,
  sort: 'distance',
  order: 'desc',
  latitude,
  longitude,
  t: new Date().getTime()
},'POST');

export const finishOrder = (user_id, orderid) => fetch('/bos/v1/users/' + user_id + '/orders/' + orderid + '/finish');


/**
 * 获取订单详情
 */

export const getOrderDetail = (orderid) => fetch('/rider/getOrder/',{orderid});

export const checkOrder = (userid,orderid) => fetch('/rider/checkOrder',{userid,orderid},'POST')

export const sendOrder = (userid,orderid) => fetch('/rider/sendorder',{userid,orderid},'POST')

/**
 *个人中心里编辑地址
 */

export const getAddressList = (user_id) => fetch('/v1/users/' + user_id + '/addresses')

/**
 *个人中心里搜索地址
 */

export const getSearchAddress = (keyword) => fetch('v1/pois', {
  keyword: keyword,
  type: 'nearby'
})

/**
 * 删除地址
 */

export const deleteAddress = (userid, addressid) => fetch('/v1/users/' + userid + '/addresses/' + addressid, {}, 'DELETE')


/**
 * 账号密码登录
 */
export const riderLogin = (username, password, captchaCode, captchCodeId) => fetch('/rider/login', {username, password, captchaCode, captchCodeId}, 'POST');

export const rideronline = (riderid ) => fetch('/rider/online', {riderid,}, 'POST');

export const rideroffline = (riderid) => fetch("/rider/offline",{riderid},'POST')

/**
 * 退出登录
 */
export const signout = () => fetch('/v1/users/v2/signout');

/**
 * 改密码
 */
export const changePassword = (username, oldpassWord, newpassword, confirmpassword, captcha_code) => fetch('/rider/changepassword', {
  username,
  oldpassWord,
  newpassword,
  confirmpassword,
  captcha_code
}, 'POST');

export const changemobile = (riderid, mobile) =>fetch('/rider/'+riderid+'/updatemobile/'+mobile, {riderid,mobile}, 'POST');
