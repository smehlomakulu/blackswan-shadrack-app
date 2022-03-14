package com.blackswandata.usertask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class UserTasksApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserTasksApplication.class, args);
    }
}
