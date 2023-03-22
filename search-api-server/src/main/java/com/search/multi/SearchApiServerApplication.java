package com.search.multi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SearchApiServerApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-API-KEY,application,application-core");
        SpringApplication.run(SearchApiServerApplication.class, args);
    }

}
