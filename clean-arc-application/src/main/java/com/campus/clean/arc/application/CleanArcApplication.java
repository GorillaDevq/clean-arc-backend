package com.campus.clean.arc.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication(scanBasePackages = {"com.rcore", "ru.foodtechlab", "io.foodtechlab", "com.campus"})
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableFeignClients(basePackages = {"ru.foodtechlab", "io.foodtechlab"})
public class CleanArcApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CleanArcApplication.class, args);
    }
}
