package dev.test.redis_cache;

import dev.test.redis_cache.entity.CustomerEntity;
import dev.test.redis_cache.redis.RedisRepository;
import dev.test.redis_cache.redis.UserRedis;
import dev.test.redis_cache.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("/api")
@Slf4j
public class RedisCacheApplication {

	private CustomerRepository customerRepository;

	private RedisRepository redisRepository;	// truy vấn redis tương tự như mysql

	private RedisTemplate<String, Serializable> redisTemplate;	// xử lý trực tiếp : deserializer, connect, ...

	@Autowired
	public RedisCacheApplication(CustomerRepository customerRepository, RedisRepository redisRepository, RedisTemplate<String, Serializable>  redisTemplate) {
		this.customerRepository = customerRepository;
		this.redisRepository = redisRepository;
		this.redisTemplate = redisTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisCacheApplication.class, args);
	}

	@GetMapping("/{id}")
	public CustomerEntity getCustomer(@PathVariable Long id) {
		return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found customer with id: " + id));
	}

	@PostMapping("")
	public CustomerEntity save(@RequestBody CustomerEntity customer) {
		System.out.println(customer);
		return customerRepository.save(customer);
	}

	@PostMapping("/redis")
	public UserRedis save2Redis(@RequestBody UserRedis userRedis) {
		return redisRepository.save(userRedis);

	} 

	@PostMapping("/redis-template/{key}")
	public UserRedis save2Redistemplate(@RequestBody UserRedis userRedis, @PathVariable String key) {
		System.out.println("call api redis template with info: " + userRedis);
		redisTemplate.opsForValue().set(key,  userRedis);
		return userRedis;
	}

	@GetMapping("/redis")
	@Cacheable(cacheNames = "customer")
	public Iterable<UserRedis> findAll() {
		System.out.println("get all obj redis");
		return redisRepository.findAll();
//		return redisTemplate.get
	}

	@GetMapping("/redis/{id}")
	public UserRedis findById(@PathVariable Long id) {
		System.out.println("get by id obj redis");
		return redisRepository.findById(id).orElseThrow(RuntimeException::new);
//		return redisTemplate.get
	}


	@Bean
	public CommandLineRunner commandLineRunner() {
		return commandLineRunner -> {
//			getCustomer(1L);
//			System.out.println("say: Hello");
		};
	}
}
