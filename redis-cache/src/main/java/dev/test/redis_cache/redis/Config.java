package dev.test.redis_cache.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

import java.io.Serializable;
import java.util.Collections;


@Configuration
public class Config {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public LettuceConnectionFactory redisStandAloneConnectionFactory() {

//        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//                .useSsl().and()
//                .commandTimeout(Duration.ofSeconds(2))
//                .shutdownTimeout(Duration.ZERO)
//                .build();
//
//        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port), clientConfig);
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port));

    }

    @Bean
    public RedisTemplate<String, Serializable> redisTemplateStandAlone(@Qualifier("redisStandAloneConnectionFactory") LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String , Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

//    @Bean
//    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//
//        RedisCacheManager cacheManager = RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())
//                .transactionAware()
//                .withInitialCacheConfigurations(Collections.singletonMap("predefined",
//                        RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()))
//                .build();
//        return RedisCacheManager.create(connectionFactory);
//    }

//    static class JsonRedisSerializer implements RedisSerializer<Object> {
//
//        private final ObjectMapper om;
//
//        public JsonRedisSerializer() {
//            this.om = new ObjectMapper().enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
//        }
//
//        @Override
//        public byte[] serialize(Object t) throws SerializationException {
//            try {
//                System.out.println("from serialize: " + t);
//                return om.writeValueAsBytes(t);
//            } catch (JsonProcessingException e) {
//                throw new SerializationException(e.getMessage(), e);
//            }
//        }
//
//        @Override
//        public Object deserialize(byte[] bytes) throws SerializationException {
//
//            if(bytes == null){
//                return null;
//            }
//
//            try {
//                System.out.println("from deserialize: " + bytes);
//                return om.readValue(bytes, Object.class);
//            } catch (Exception e) {
//                throw new SerializationException(e.getMessage(), e);
//            }
//        }
//    }


}
