package com.blackswandata.usertask.service;

import com.blackswandata.usertask.Exception.TaskCreationFailureException;
import com.blackswandata.usertask.Exception.TaskNotFoundException;
import com.blackswandata.usertask.Exception.UserNotFoundException;
import com.blackswandata.usertask.entity.Task;
import com.blackswandata.usertask.entity.User;
import com.blackswandata.usertask.repository.TaskRepository;
import com.blackswandata.usertask.repository.UserRepository;
import com.blackswandata.usertask.request.TaskRequest;
import com.blackswandata.usertask.request.UserRequest;
import com.blackswandata.usertask.response.BaseResponse;
import com.blackswandata.usertask.response.TaskResponse;
import com.blackswandata.usertask.response.UserTaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class TaskServiceImpl implements TaskService{

    private UserRepository userRepository;
    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public BaseResponse createTask(Long userId, TaskRequest taskRequest) throws Exception{
        Task task = null;
        User user = userRepository.findById(userId).orElse(null);
        BaseResponse response = null;
        //Set<User> users = new HashSet<>();
        if(user != null) {
            task = new Task();
            task.setName(taskRequest.getName());
            task.setDescription(taskRequest.getDescription());
            //users.add(user);
            //task.setUsers(users);
            user.addTask(task);
            user = userRepository.save(user);
            Set<Task> userTasks = user.getTasks();
            if(userTasks.size() == 1) {
                task = userTasks.stream().iterator().next();
                response = new TaskResponse(task.getId(), task.getName(), task.getDescription(), task.getDate().toString());
            } else if(userTasks.size() > 1){
                Set<TaskResponse> tasks = new HashSet<>();
                userTasks.forEach(ut -> {
                    TaskResponse taskResponse = new TaskResponse(ut.getId(), ut.getName(), ut.getDescription(),
                                                                                           ut.getDate().toString());
                    tasks.add(taskResponse);
                });
                response = new UserTaskResponse(user.getId(), user.getFirstName(),
                                                user.getUsername(), user.getLastName(), tasks);
            } else {
                throw new TaskCreationFailureException(String.format("Failed to create Task for User with ID: [%s]", userId));
            }
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
            user.addTask(task);
            userRepository.save(user);

            TaskResponse taskResponse = new TaskResponse(task.getId(), task.getName(),
                                                         task.getDescription(), task.getDate().toString());
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
                                                     task.getDescription(), task.getDate().toString());
        return taskResponse;
    }

    @Override
    public void deleteTaskByUser(Long userId, Long taskId) throws Exception {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new UserNotFoundException(String.format("User with ID: [%s] could not be found", userId)));
        user.removeTask(taskId);
        userRepository.save(user);
    }
}
