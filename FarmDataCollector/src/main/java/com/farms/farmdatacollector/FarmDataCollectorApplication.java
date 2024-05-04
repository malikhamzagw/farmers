package com.farms.farmdatacollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = "com.farms.farmdatacollector.model")
public class FarmDataCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FarmDataCollectorApplication.class, args);
    }

}
