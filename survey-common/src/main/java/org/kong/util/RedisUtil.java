package org.kong.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    public void setData(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setDataExpire(String key, String value, long duration) {
        redisTemplate.opsForValue().set(key, value, duration, TimeUnit.MILLISECONDS);
    }

    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void expire(String key, long duration) {
        redisTemplate.expire(key, duration, TimeUnit.MILLISECONDS);
    }
} 