package com.roy.sqwaimai.core.entity.vo;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVo implements Serializable {
    private String username;
    private String password;
    private String captchaCode;
    private String captchCodeId;
}