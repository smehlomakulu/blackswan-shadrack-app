package com.blackswandata.usertask.service;

import com.blackswandata.usertask.controller.UserController;
import com.blackswandata.usertask.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskStatusUpdateScheduler {

    private static final Logger logger = LoggerFactory.getLogger(TaskStatusUpdateScheduler.class);

    private TaskService service;

    public TaskStatusUpdateScheduler(TaskService service) {this.service = service;}

    @Scheduled(cron = "${cron.expression}")
    public void updateTaskStatus() {
        logger.info("Scheduler started...");
        service.updateTaskStatus();
    }
}
