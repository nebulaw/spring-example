package com.example.jpaspringdemo.aop;

import org.springframework.stereotype.Component;

@Component
public class TestPojo {

  @Loggable
  public void test() {
    System.out.println("Test method called");
    testUtil("no name");
  }

  @Loggable
  public void testUtil(String name) {
    System.out.println("TestUtil method called for " + name);
  }

}
