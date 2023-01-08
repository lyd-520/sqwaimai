package com.roy.sqwaimai.core.util;

public interface Constants {
    String TOKEN_NAME ="Authorization";
    String USER_TYPE_MGR= "1";
    String USER_TYPE_SHOP="2";
    Long ROLE_ID_SHOP = 3L;
    long SYSTEM_USER_ID=-1;

    /**
     * 用户密码加密key
     */
    String CRYPT_DES_KEY = "sc123456";

    /**
     * 定义JSON形式的业务处理结果----附加上信息
     */
    static String msg(String state, Object info) {
        return state.replace("}", ", 'info': '" + info + "'}");
    }
}
