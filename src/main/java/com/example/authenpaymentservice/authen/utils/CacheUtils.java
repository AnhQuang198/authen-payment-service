package com.example.authenpaymentservice.authen.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class CacheUtils {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void set(String key, Object value, long expireTime) throws IOException {
        redisTemplate
                .opsForValue()
                .set(key, value.toString(), expireTime, TimeUnit.MILLISECONDS);
    }

    public String get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isExisted(String key) {
        return redisTemplate.hasKey(key);
    }

    public void del(String key) {
        redisTemplate.delete(key);
    }
}
