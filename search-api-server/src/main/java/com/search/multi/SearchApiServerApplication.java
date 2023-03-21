package com.search.multi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearchApiServerApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application,application-core");
        SpringApplication.run(SearchApiServerApplication.class, args);
    }

}
