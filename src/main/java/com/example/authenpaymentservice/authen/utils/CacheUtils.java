package com.example.authenpaymentservice.authen.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Log4j2
public class CacheUtils {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void set(String key, Object value, long expireTime) throws IOException {
        redisTemplate
                .opsForValue()
                .set(key, JsonParser.toJson(value), expireTime, TimeUnit.MILLISECONDS);
    }

    public String get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.info("Get key Redis error: {}", e);
            e.printStackTrace();
            return null;
        }
    }

    public <T> T get(String key, Class<T> tClass) throws Exception {
        try {
            String value = redisTemplate.opsForValue().get(key);
            return JsonParser.entity(value, tClass);
        } catch (Exception e) {
            log.info("Get key Redis error: {}", e);
            e.printStackTrace();
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
