package com.blackswandata.usertask.service;

import com.blackswandata.usertask.entity.Task;
import com.blackswandata.usertask.request.TaskRequest;
import com.blackswandata.usertask.response.BaseResponse;
import com.blackswandata.usertask.response.TaskResponse;

import java.util.List;
import java.util.Set;

public interface TaskService {

    BaseResponse createTask(Long userId, TaskRequest task) throws Exception;

    TaskResponse updateTask(Long userId, Long taskId, TaskRequest request) throws Exception;

    Set<TaskResponse> findAllByUser(Long userId) throws Exception;

    TaskResponse findTaskByUser(Long userId, Long taskId) throws Exception;

    void deleteTaskByUser(Long userId, Long taskId) throws Exception;

}
