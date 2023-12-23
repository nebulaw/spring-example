package com.example.jpaspringdemo;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class JpaSpringDemoApplication extends SpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaSpringDemoApplication.class, args);
    }

}
