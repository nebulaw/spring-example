package com.example.jpaspringdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JpaSpringDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(JpaSpringDemoApplication.class, args);
  }

}
