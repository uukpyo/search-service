package com.search.multi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CoreApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-API-KEY,application-core");
        SpringApplication.run(CoreApplication.class, args);
    }
}
