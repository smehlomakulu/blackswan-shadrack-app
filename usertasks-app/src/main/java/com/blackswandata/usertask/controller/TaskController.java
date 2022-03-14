package com.blackswandata.usertask.controller;

import com.blackswandata.usertask.Exception.TaskCreationFailureException;
import com.blackswandata.usertask.Exception.TaskNotFoundException;
import com.blackswandata.usertask.Exception.UserNotFoundException;
import com.blackswandata.usertask.request.TaskRequest;
import com.blackswandata.usertask.response.BaseResponse;
import com.blackswandata.usertask.response.TaskResponse;
import com.blackswandata.usertask.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@ControllerAdvice
@RequestMapping("/api/user")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService service) {this.taskService = service;}

    @PostMapping("/{userId}/task")
    public ResponseEntity<? extends Object> createTask(@PathVariable("userId") Long userId,@RequestBody TaskRequest request) {
        BaseResponse response;
        logger.debug("CreateTask Process for User: [{}]",userId);
        try {
            response = taskService.createTask(userId, request);
            logger.debug("Task with ID: [{}] created for User with ID: [{}]",response.getId(),userId);
        } catch (Exception e) {
            if (e instanceof TaskCreationFailureException || e instanceof UserNotFoundException) {
                logger.debug("Task creation failed due to: "+ e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
            } else {
                logger.error("Failed to create task for user", e.getStackTrace());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{userId}/task/{taskId}")
    public ResponseEntity<? extends Object> updateTask(@PathVariable("userId") Long userId,
                                                       @PathVariable("taskId") Long taskId, @RequestBody TaskRequest request) {
        TaskResponse taskResponse;
        logger.debug("UpdateTask Process for User: [{}] with Task [{}]",userId, taskId);
        try {
            taskResponse = taskService.updateTask(userId, taskId, request);
            logger.info("Successfully updated task!");
        } catch (Exception e) {
            if (e instanceof TaskNotFoundException || e instanceof UserNotFoundException) {
                logger.error("Failed to update Task [{}] for User [{}]", taskId, userId);
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            } else {
                logger.error("Failed to update task for user", e.getStackTrace());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/task/{taskId}")
    public ResponseEntity deleteTask(@PathVariable("userId") Long userId, @PathVariable("taskId") Long taskId) {
        logger.debug("Delete Task Process for User: [{}] with Task [{}]",userId, taskId);
        try {
            taskService.deleteTaskByUser(userId, taskId);
            logger.info("Task successfully deleted.");
        } catch (Exception e) {
            if (e instanceof TaskNotFoundException || e instanceof UserNotFoundException) {
                logger.error("Failed to delete Task [{}] for User [{}]", taskId, userId);
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            } else {
                logger.error("Failed to delete task for user", e.getStackTrace());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}/task/{taskId}")
    public ResponseEntity<? extends Object> getTaskInfo(@PathVariable("userId") Long userId, @PathVariable("taskId") Long taskId) {
        logger.debug("GetTaskInfo Process for User: [{}] with Task [{}]",userId, taskId);
        TaskResponse taskResponse = null;
        try {
            taskResponse = taskService.findTaskByUser(userId, taskId);
            logger.info("Task info with ID [{}] for user [{}] successfully retrieved", userId, taskId);
        } catch (Exception e) {
            if (e instanceof TaskNotFoundException || e instanceof UserNotFoundException) {
                logger.error("Task with ID [{}] for User [{}] not found", taskId, userId);
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            } else {
                logger.error("Failed to retrieve task for user", e.getStackTrace());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @GetMapping("/{userId}/task")
    public ResponseEntity<Set<TaskResponse>> listAllByUser(@PathVariable("userId") Long userId) {
        logger.debug("ListTasks Process for User: [{}]",userId);
        Set<TaskResponse> taskList = null;
        try {
            return new ResponseEntity<>(taskService.findAllByUser(userId), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof TaskNotFoundException || e instanceof UserNotFoundException) {
                logger.error("Tasks for User [{}] not found", userId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                logger.error("Tasks for user not found", e.getStackTrace());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
