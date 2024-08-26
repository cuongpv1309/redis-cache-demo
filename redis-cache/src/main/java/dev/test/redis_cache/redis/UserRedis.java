package dev.test.redis_cache.redis;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.lang.annotation.Documented;

@Data
@RedisHash(value = "customer")
public class UserRedis implements Serializable {

    @Id
    private Long id;
    private String name;
    private String email;
    private int age;

}
