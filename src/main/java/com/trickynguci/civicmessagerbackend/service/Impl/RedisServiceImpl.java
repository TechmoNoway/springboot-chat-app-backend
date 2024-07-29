package com.trickynguci.civicmessagerbackend.service.Impl;

import com.trickynguci.civicmessagerbackend.service.RedisService;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setUserStatus(int userId, String status) {
        redisTemplate.opsForValue().set(userId + "", status, 3, TimeUnit.DAYS);
    }

    public String getUserStatus(int userId) {
        Object status = redisTemplate.opsForValue().get(String.valueOf(userId));
        return status != null ? status.toString() : null;
    }
}
