package com.khangocd99.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.khangocd99.ecommerce.model.persistence.repositories")
@EntityScan("com.khangocd99.ecommerce.model.persistence")
@SpringBootApplication
public class SareetaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SareetaApplication.class, args);
	}

}
