package com.example.jpaspringdemo;

import com.example.jpaspringdemo.aop.TestPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JpaSpringDemoApplication implements CommandLineRunner {

  @Autowired
  private TestPojo testPojo;

  public static void main(String[] args) {
    SpringApplication.run(JpaSpringDemoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    testPojo.test();
    System.out.println("Test passed");
    testPojo.testUtil("Karlo");
  }
}
