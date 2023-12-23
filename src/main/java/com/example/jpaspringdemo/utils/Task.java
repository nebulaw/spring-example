package com.example.jpaspringdemo.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableAsync
@Component
public class Task {

    // every 10 seconds
    @Async
    @Scheduled(cron = "0/10 * * * * ?")
    public void printCurrentTime() {
        System.out.println("Every 10 seconds method executed at " + LocalDateTime.now());
    }

    // every 1 second
    @Scheduled(cron = "0/1 * * * * ?")
    public void printCurrentTime1() {
        System.out.println("Every 1 second method executed at " + LocalDateTime.now());
    }

    // Tuesday
    @Scheduled(cron = "0 0 17 * * 2")
    public void printTuesday() {
        System.out.println("Tuesday method executed at " + LocalDateTime.now());
    }

    // 00:00am 1 January
    @Scheduled(cron = "0 0 0 1 1 ?")
    public void printJanuary() {
        System.out.println("Happy New Year! It's " + LocalDateTime.now());
    }

    // every 3 month
    @Scheduled(cron = "0 0 0 1 2/3 ?")
    public void printIn3Month() {
        System.out.println("New Season! Time is " + LocalDateTime.now());
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.setThreadNamePrefix("Task-");
        return taskScheduler;
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newSingleThreadExecutor();
    }

}
