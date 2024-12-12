package org.kvn.Redis_Project.repository;

import org.kvn.Redis_Project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class UserCacheRepository {

    @Value("${redis.user.details.timeout}")
    private int timeout;

    private String userKey = "user";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setUser(int id, User user) {
        String key = userKey + String.valueOf(id);
        redisTemplate.opsForValue().set(key, user, 10, TimeUnit.MINUTES);
    }

    public User getUser(int id) {
        String key = userKey + String.valueOf(id);
        return (User) redisTemplate.opsForValue().get(key);
    }

    public void deleteUser(int id) {
        String key = userKey + String.valueOf(id);
        redisTemplate.expire(key, 1, TimeUnit.MILLISECONDS);
    }
}
