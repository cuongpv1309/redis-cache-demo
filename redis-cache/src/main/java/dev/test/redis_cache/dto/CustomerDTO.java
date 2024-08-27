package dev.test.redis_cache.dto;

import dev.test.redis_cache.entity.CustomerEntity;
import dev.test.redis_cache.redis.UserRedis;

public record CustomerDTO(
        String name,
        String email,
        int age
) {
    public CustomerDTO(CustomerEntity c) {
        this(c.getName(), c.getEmail(), c.getAge());
    }

    public CustomerDTO(UserRedis userRedis) {
        this(userRedis.getName(), userRedis.getEmail(), userRedis.getAge());
    }
}
