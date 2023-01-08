package com.roy.sqwaimai.core.entity.vo;
import lombok.Data;

@Data
public class LoginVo{
    private String username;
    private String password;
    private String captchaCode;
    private String captchCodeId;
}