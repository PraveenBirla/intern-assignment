package com.grid07.api.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class GuardrailService {

    private final StringRedisTemplate redisTemplate;

    public GuardrailService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean checkBotLimit(Long postId){
        String key = "post:" + postId + ":bot_count";

        Long count = redisTemplate.opsForValue().increment(key);

        return count <= 100;
    }

    public void validateDepth(int depthLevel) {
        if (depthLevel > 20) {
            throw new RuntimeException("Depth limit exceeded");
        }
    }

    public boolean checkCooldown(Long botId, Long userId) {

        String key = "cooldown:bot_" + botId + ":user_" + userId;

        Boolean exists = redisTemplate.hasKey(key);

        if (exists != null && exists) {
            return false;
        }

        redisTemplate.opsForValue()
                .set(key, "1", Duration.ofMinutes(10));

        return true;
    }

}
