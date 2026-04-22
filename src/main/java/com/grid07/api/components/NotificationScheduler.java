package com.grid07.api.components;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationScheduler {

    private final StringRedisTemplate redisTemplate;


    public NotificationScheduler(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Scheduled(fixedRate = 300000)
    public void processNotifications() {


        List<Long> userIds = List.of(1L, 2L, 3L);

        for (Long userId : userIds) {

            String listKey = "user:" + userId + ":pending_notifs";
            String firstMessage = redisTemplate.opsForList().index(listKey, 0);
            Long size = redisTemplate.opsForList().size(listKey);
            long others = size-1;
            if (size != null && size > 0) {


                System.out.println(
                        "Summarized Push Notification: Bot and "
                                + size + " others interacted with your posts."
                );


                redisTemplate.delete(listKey);
            }
        }
    }
}
