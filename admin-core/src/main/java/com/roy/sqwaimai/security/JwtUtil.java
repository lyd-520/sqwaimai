package com.roy.sqwaimai.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.roy.sqwaimai.bean.entity.system.User;
import com.roy.sqwaimai.core.entity.Shop;
import com.roy.sqwaimai.core.entity.sys.AccountInfo;
import com.roy.sqwaimai.core.util.Constants;
import com.roy.sqwaimai.utils.HttpKit;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JwtUtil {
    // 过期时间60分钟
    private static final long EXPIRE_TIME = 60 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取token中的账号信息
     *
     * @param token
     * @return
     */
    public static AccountInfo getAccountInfo(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String username = jwt.getClaim("username").asString();
            Long userId = jwt.getClaim("userId").asLong();
            String userType = jwt.getClaim("userType").asString();
            String password = jwt.getClaim("password").asString();
            return new AccountInfo(username, userId, userType,password);
        } catch (JWTDecodeException e) {
//            return null;
            return new AccountInfo();
        }
    }
    public static AccountInfo getAccountInfo(   ) {
       return getAccountInfo(HttpKit.getToken());
    }

    public static Long getUserId() {
        return getUserId(HttpKit.getToken());
    }

    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     *
     * @param user 用户
     * @return 加密的token
     */
    public static String sign(User user) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(user.getPassword());
            // 附带username信息
            return JWT.create()
                    .withClaim("username", user.getAccount())
                    .withClaim("userId", user.getId())
                    .withClaim("userType", Constants.USER_TYPE_MGR)
                    .withClaim("password", user.getPassword())
//                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 生成签名
     *
     * @param shop 用户
     * @return 加密的token
     */
    public static String sign(Shop shop) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(shop.getPassword());
            // 附带username信息
            return JWT.create()
                    .withClaim("username", shop.getName())
                    .withClaim("userId", shop.getId())
                    .withClaim("userType", Constants.USER_TYPE_SHOP)
                    .withClaim("password", shop.getPassword())
//                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
