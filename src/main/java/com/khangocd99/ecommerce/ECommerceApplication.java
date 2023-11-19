package com.khangocd99.ecommerce;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableJpaRepositories("com.khangocd99.ecommerce.model.persistence.repositories")
@EntityScan("com.khangocd99.ecommerce.model.persistence")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ECommerceApplication {

    private static final Logger logger = LogManager.getLogger(ECommerceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
        logger.info("E-Commerce Application has been started!");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        logger.info("BCryptPasswordEncoder Bean has been created!");
        return new BCryptPasswordEncoder();
    }
}