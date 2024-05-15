package com.example.chatserver.framework.impl;

import com.example.chatserver.framework.InmemoryDatabaseFramework;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisFrameworkImpl implements InmemoryDatabaseFramework {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisFrameworkImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void setKey(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
