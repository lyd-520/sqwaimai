package com.roy.sqwaimai.app.config;

import com.roy.sqwaimai.security.JwtUtil;
import com.roy.sqwaimai.utils.Constants;
import com.roy.sqwaimai.utils.HttpKit;
import com.roy.sqwaimai.utils.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 审计功能配置
 */
@Configuration
public class UserIDAuditorConfig implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            String token = HttpKit.getRequest().getHeader(Constants.TOKEN_NAME);
            if (StringUtils.isNotEmpty(token)) {
                return Optional.of(JwtUtil.getUserId(token));
            }
        }catch (Exception e){
            //返回系统用户id
            return Optional.of(Constants.SYSTEM_USER_ID);
        }
        //返回系统用户id
        return Optional.of(Constants.SYSTEM_USER_ID);
    }
}
