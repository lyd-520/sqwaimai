import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  // return Cookies.set(TokenKey, token)
  let expires = new Date(new Date()*1 + 60 * 60* 1000)
  return Cookies.set(TokenKey,token,expires)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
