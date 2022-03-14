package com.blackswandata.usertask.service;

import com.blackswandata.usertask.Exception.UserNotFoundException;
import com.blackswandata.usertask.entity.User;
import com.blackswandata.usertask.repository.UserRepository;
import com.blackswandata.usertask.request.UserRequest;
import com.blackswandata.usertask.response.UserResponse;
import jdk.nashorn.internal.runtime.options.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        User newUser = new User();
        newUser.setUsername(request.getUserName());
        newUser.setFirstName(request.getName());
        newUser.setLastName(request.getLastName());
        User user = userRepository.save(newUser);
        return new UserResponse(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName());
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest request) throws Exception{
        User updateUser;
        logger.debug("Username-{}, name-{}, lastname-{}",request.getUserName(), request.getName(), request.getLastName());
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            updateUser = user.get();
            updateUser.setUsername(request.getUserName());
            updateUser.setFirstName(request.getName());
            updateUser.setLastName(request.getLastName());
            User newUser = userRepository.save(updateUser);
            return new UserResponse(newUser.getId(), newUser.getFirstName(),
                                    newUser.getUsername(), newUser.getLastName());
        } else {
            throw new UserNotFoundException(String.format("Failed to find user with ID: [%d]", userId));
        }
    }

    @Override
    public List<UserResponse> listAllUsers() {
        List<UserResponse> users = new ArrayList<>();
        userRepository.findAll().forEach(u -> {
            UserResponse user = new UserResponse(u.getId(), u.getUsername(), u.getLastName(), u.getLastName());
            users.add(user);
        });
        return users;
    }

    @Override
    public UserResponse getUser(Long id) throws Exception {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new UserNotFoundException(String.format("Failed to find user with ID: [%d]", id)));
        return new UserResponse(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName());
    }
}
