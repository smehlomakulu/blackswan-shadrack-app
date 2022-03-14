package com.blackswandata.usertask.service;

import com.blackswandata.usertask.Exception.TaskCreationFailureException;
import com.blackswandata.usertask.Exception.TaskNotFoundException;
import com.blackswandata.usertask.Exception.UserNotFoundException;
import com.blackswandata.usertask.TaskStatus;
import com.blackswandata.usertask.entity.Task;
import com.blackswandata.usertask.entity.User;
import com.blackswandata.usertask.repository.TaskRepository;
import com.blackswandata.usertask.repository.UserRepository;
import com.blackswandata.usertask.request.TaskRequest;
import com.blackswandata.usertask.request.UserRequest;
import com.blackswandata.usertask.response.BaseResponse;
import com.blackswandata.usertask.response.TaskResponse;
import com.blackswandata.usertask.response.UserTaskResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class TaskServiceImpl implements TaskService{

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private UserRepository userRepository;
    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public BaseResponse createTask(Long userId, TaskRequest taskRequest) throws Exception{

        Task task;
        User user = userRepository.findById(userId).orElse(null);
        BaseResponse response;
        if(user != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(taskRequest.getDate(), formatter);

            logger.debug("Task: name-{}, description-{}, status-{}, date-{}", taskRequest.getName(),
                                                                              taskRequest.getDescription(),
                                                                              taskRequest.getStatus(), taskRequest.getDate());
            task = new Task();
            task.setName(taskRequest.getName());
            task.setDescription(taskRequest.getDescription());
            task.setStatus(TaskStatus.valueOf(taskRequest.getStatus().toUpperCase(Locale.ROOT)));
            task.setDate(dateTime);
            user.addTask(task);
            task = taskRepository.save(task);
            Set<Task> userTasks = user.getTasks();
            response = new TaskResponse(task.getId(), task.getName(), task.getDescription(),
                                        task.getStatus().status(),task.getDate().toString());

        } else {
            throw new UserNotFoundException(String.format("User with the ID: [%s] not found", userId));
        }
        return response;
    }

    @Override
    public TaskResponse updateTask(Long userId, Long taskId, TaskRequest request) throws Exception{
        Task task;
        User user = userRepository.findById(userId).orElse(null);
        if(user != null) {
            task = user.getTask(user.getTasks(), taskId)
                       .orElseThrow(() ->new TaskNotFoundException(String.format("Task with ID [%d] for user ID [%d] not found", taskId, userId)));;

            task.setName(request.getName());
            task.setStatus(TaskStatus.valueOf(request.getStatus().toUpperCase(Locale.ROOT)));
            task.setDescription(request.getDescription());
            user.addTask(task);
            task = taskRepository.save(task);

            TaskResponse taskResponse = new TaskResponse(task.getId(), task.getName(),
                                                         task.getDescription(), task.getStatus().status(),
                                                         task.getDate().toString());
            return taskResponse;
        } else {
            throw new UserNotFoundException(String.format("User with ID: [%s] could not be found", userId));
        }
    }

    @Override
    public Set<TaskResponse> findAllByUser(Long userId) throws Exception{
        User user = userRepository.findById(userId).orElse(null);
        if(user != null) {
            Set<Task> tasks = user.getTasks();
            Set<TaskResponse> response = new HashSet<>();
            if(!tasks.isEmpty()) {
                tasks.forEach(t -> {
                    TaskResponse taskResponse = new TaskResponse(t.getId(), t.getName(), t.getDescription(),
                                                                                         t.getStatus().status(),
                                                                                         t.getDate().toString());
                    response.add(taskResponse);
                });
            }
            return response;
        } else {
            throw new UserNotFoundException(String.format("User with ID: [%s] could not be found", userId));
        }
    }

    @Override
    public TaskResponse findTaskByUser(Long userId, Long taskId) throws Exception{
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new UserNotFoundException(String.format("User with ID: [%s] could not be found", userId)));;

        Task task = user.getTask(user.getTasks(), taskId)
                        .orElseThrow(() ->new TaskNotFoundException(String.format("Task with ID [%d] for user ID [%d] not found", taskId, userId)));

        TaskResponse taskResponse = new TaskResponse(task.getId(), task.getName(),
                                                     task.getDescription(), task.getStatus().status(),
                                                     task.getDate().toString());
        return taskResponse;
    }

    @Override
    public void deleteTaskByUser(Long userId, Long taskId) throws Exception {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new UserNotFoundException(String.format("User with ID: [%s] could not be found", userId)));
        user.removeTask(taskId);
        userRepository.save(user);
    }

    @Override
    public void updateTaskStatus() {
        List<Task> tasks = taskRepository.findOutdatedTaskStatus();
        tasks.forEach(t -> {
            logger.debug("Task with ID {} has {} status", t.getId(), t.getStatus());
            t.setStatus(TaskStatus.DONE);
            taskRepository.save(t);
        });
    }
}
