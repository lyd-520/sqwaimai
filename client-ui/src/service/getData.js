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
 * 获取msite页面食品分类列表
 */

export const msiteFoodTypes = geohash => fetch('/api/entry/index_entry', {
  geohash,
  group_type: '1'
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
    // 'extras': 'activities',
    // keyword: '',
    restaurant_category_id,
    // 'restaurant_category_ids': restaurant_category_ids,
    order_by,
    // 'delivery_mode': delivery_mode + supportStr
  };
  return fetch('/api/shop/restaurants', data);
};


/**
 * 获取search页面搜索结果
 */

export const searchRestaurant = (geohash, keyword) => fetch('/api/shop/searchShop', {
  'extras': 'restaurant_activity',
  geohash,
  keyword,
  type: 'search'
});


/**
 * 获取food页面的 category 种类列表
 */

export const foodCategory = (latitude, longitude) => fetch('/api/shop/listcategory', {});

/**
 * 获取food页面的配送方式
 */

export const foodDelivery = (latitude, longitude) => fetch('/api/delivery/delivery_modes', {
  latitude,
  longitude,
  kw: ''
});


/**
 * 获取food页面的商家属性活动列表
 */

export const foodActivity = (latitude, longitude) => fetch('/api/activity/nearbyactivities', {
  latitude,
  longitude,
  kw: ''
});


/**
 * 获取shop页面商铺详情
 */

export const shopDetails = (shopid, latitude, longitude) => fetch('/api/shop/queryshop/' + shopid, {
  latitude,
  longitude: longitude + '&extras=activities&extras=album&extras=license&extras=identification&extras=statistics'
});


/**
 * 获取shop页面菜单列表
 */

export const foodMenu = restaurant_id => fetch('/api/shop/getmenu', {
  restaurant_id
});


/**
 * 获取商铺评价列表
 */

export const getRatingList = (shopid, offset, tag_name = '') => fetch('/api/ratings/restaurants/' + shopid +'/ratings', {
  has_content: true,
  offset,
  limit: 10,
  tag_name
});


/**
 * 获取商铺评价分数
 */

export const ratingScores = shopid => fetch('/api/ratings/restaurants/' + shopid + '/scores');


/**
 * 获取商铺评价分类
 */

export const ratingTags = shopid => fetch('/api/ratings/restaurants/' + shopid + '/tags');


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
 * 确认订单
 */

export const checkout = (geohash, entities, shopid) => fetch('/api/carts/checkorder', {
  come_from: "web",
  geohash,
  entities,
  restaurant_id: shopid,
}, 'POST');


/**
 * 获取快速备注列表
 */

export const getRemark = (id, sig) => fetch('/api/carts/' + id + '/remarks', {
  sig
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
 * 下订单
 */

export const placeOrders = (user_id, cart_id, address_id, description, entities, geohash, sig) => fetch('/api/orders/cartorder/' + user_id + '/' + cart_id, {
  address_id,
  come_from: "mobile_web",
  deliver_time: "",
  description,
  entities,
  geohash,
  paymethod_id: 1,
  sig,
}, 'POST');


/**
 * 重新发送订单验证码
 */

export const rePostVerify = (cart_id, sig, type) => fetch('/v1/carts/' + cart_id + '/verify_code', {
  sig,
  type,
}, 'POST');


/**
 * 下订单
 */

export const validateOrders = ({
                                 user_id,
                                 cart_id,
                                 address_id,
                                 description,
                                 entities,
                                 geohash,
                                 sig,
                                 validation_code,
                                 validation_token
                               }) => fetch('/api/orders/cartorder/' + user_id + '/' + cart_id, {
  address_id,
  come_from: "mobile_web",
  deliver_time: "",
  description,
  entities,
  geohash,
  paymethod_id: 1,
  sig,
  validation_code,
  validation_token,
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
 * 支付订单
 */
export const payOrder = (merchantOrderNo) => fetch('/payapi/payment/payOrder', {
  merchantOrderNo
}, 'POST');


/**
 * 获取服务中心信息
 */

export const getService = () => fetch('/api/profile/explain');


/**
 *兑换会员卡
 */

export const vipCart = (id, number, password) => fetch('/member/v1/users/' + id + '/delivery_card/physical_card/bind', {
  number,
  password
}, 'POST')


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

export const getUser = () => fetch('/api/users/getUserById', {user_id: getStore('user_id')});


/**
 * 手机号登录
 */

var sendLogin = (code, mobile, validate_token) => fetch('/v1/login/app_mobile', {
  code,
  mobile,
  validate_token
}, 'POST');


/**
 * 获取订单列表
 */

export const getOrderList = (user_id, offset,orderStatus) => fetch('/api/orders/userorders/' + user_id, {
  limit: 10,
  offset,
  sort:'status_code',
  order:'asc',
  t: new Date().getTime(),
  orderStatus
});

export const finishOrder = (user_id, orderid) => fetch('/api/orders/finishuserorder/' + user_id + '/' + orderid);

export const cancelOrder = (user_id, orderid) => fetch('/api/orders/canceluserorder/' + user_id + '/' + orderid);


/**
 * 获取订单详情
 */

export const getOrderDetail = (user_id, orderid) => fetch('/api/orders/snapshotuserorder/' + user_id + '/' + orderid);


/**
 *个人中心里编辑地址
 */

export const getAddressList = (user_id) => fetch('/api/address/queryUserAddress/' + user_id)

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
export const accountLogin = (username, password, captchaCode, captchCodeId) => fetch('/api/users/login', {username, password, captchaCode, captchCodeId}, 'POST');


/**
 * 退出登录
 */
export const signout = () => fetch('/api/users/signout');


/**
 * 改密码
 */
export const changePassword = (username, oldpassWord, newpassword, confirmpassword, captcha_code) => fetch('/api/users/changepassword', {
  username,
  oldpassWord,
  newpassword,
  confirmpassword,
  captcha_code
}, 'POST');
