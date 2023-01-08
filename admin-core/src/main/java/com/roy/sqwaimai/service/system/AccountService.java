package com.roy.sqwaimai.service.system;

import com.roy.sqwaimai.cache.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private TokenCache tokenCache;

    public void logout(String token) {
        tokenCache.remove(token);
    }

}
