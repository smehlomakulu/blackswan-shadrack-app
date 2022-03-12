package com.blackswandata.usertask.service;

import com.blackswandata.usertask.entity.User;
import com.blackswandata.usertask.request.UserRequest;
import com.blackswandata.usertask.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest user) throws Exception;

    UserResponse updateUser(Long userId, UserRequest request) throws Exception;

    List<UserResponse> listAllUsers();

    UserResponse getUser(Long id) throws Exception;
}
