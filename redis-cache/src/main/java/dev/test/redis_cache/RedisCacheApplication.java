package dev.test.redis_cache;

import dev.test.redis_cache.entity.CustomerEntity;
import dev.test.redis_cache.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class RedisCacheApplication {

	private CustomerRepository customerRepository;

	@Autowired
	public RedisCacheApplication(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisCacheApplication.class, args);
	}

	@GetMapping("/{id}")
	public CustomerEntity getCustomer(@PathVariable Long id) {
		return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found customer with id: " + id));
	}

	@PostMapping("")
	public CustomerEntity getCustomer(@RequestBody CustomerEntity customer) {
		System.out.println(customer);
		return customerRepository.save(customer);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return commandLineRunner -> {
//			getCustomer(1L);
			System.out.println("say: Hello");
		};
	}
}
