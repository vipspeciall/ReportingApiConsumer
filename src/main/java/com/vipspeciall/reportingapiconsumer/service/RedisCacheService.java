package com.vipspeciall.reportingapiconsumer.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisCacheService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Email ile Unique ID eşlemesini kaydet
    public void saveUniqueIdByEmail(String email, String uniqueId) {
        redisTemplate.opsForValue().set(email, uniqueId, 24, TimeUnit.DAYS); // 24 saat TTL
    }

    // Email'e ait Unique ID'yi al
    public String getUniqueIdByEmail(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    public void saveToken(String uniqueId, String token) {
        redisTemplate.opsForValue().set(uniqueId, token, 10, TimeUnit.MINUTES); // 10 dakika TTL
    }

    // Token'ı Redis'ten al
    public String getToken(String uniqueId) {
        return redisTemplate.opsForValue().get(uniqueId);
    }

    // Token'ı Redis'ten sil
    public void deleteToken(String uniqueId) {
        redisTemplate.delete(uniqueId);
    }
}
