package com.blackswandata.usertask.controller;

import com.blackswandata.usertask.Exception.TaskCreationFailureException;
import com.blackswandata.usertask.Exception.UserNotFoundException;
import com.blackswandata.usertask.request.UserRequest;
import com.blackswandata.usertask.response.UserResponse;
import com.blackswandata.usertask.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ControllerAdvice
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public UserController(UserService service) {this.userService = service;}

    @PostMapping
    public ResponseEntity<? extends Object> createUser(@RequestBody UserRequest user) {
        UserResponse response;
        logger.debug("CreateUser Process username: {}, name: {}, lasname: {}", user.getUserName(), user.getName(), user.getLastName());
        response = userService.createUser(user);
        logger.info("Successfully created user with ID: [{}]", response.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<? extends Object> updateUser(@PathVariable("id") Long id, @RequestBody UserRequest request) {
        UserResponse response;
        logger.debug("Update User Process for User with ID: [{}]", id);
        try {
            response = userService.updateUser(id, request);
            logger.info("Successfully updated user with ID: [{}]", id);
        } catch (Exception e) {
            if (e instanceof UserNotFoundException) {
                logger.debug("User update with id [{}] failed:",id);
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
            } else {
                logger.error("Failed to update user: {}", e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listAllUsers() {
        logger.debug("ListUsers Process...");
        return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<? extends Object> fingUserById(@PathVariable("id") Long id) {
        logger.debug("Find User process with ID [{}]", id);
        try {
            return new ResponseEntity<>(userService.getUser(id),HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof UserNotFoundException) {
                logger.debug("Failed to find user with ID [{}]:",id);
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
            } else {
                logger.error("Failed to find user", e.getStackTrace());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

}
