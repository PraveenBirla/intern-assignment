package com.grid07.api.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ViralityService {

    private final StringRedisTemplate stringRedisTemplate;

    public ViralityService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private String getKey(Long postId) {
        return "post:" + postId + ":virality_score";
    }

    public void updateScore(Long postId, int value) {
         stringRedisTemplate.opsForValue()
                .increment(getKey(postId), value);
    }

    public void addLike(Long postId) {
        updateScore(postId, 20);
    }

    public void addComment(Long postId) {
        updateScore(postId, 50);
    }

    public void addBotReply(Long postId) {
        updateScore(postId, 1);
    }
}
