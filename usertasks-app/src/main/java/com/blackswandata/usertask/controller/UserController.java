package com.blackswandata.usertask.controller;

import com.blackswandata.usertask.request.UserRequest;
import com.blackswandata.usertask.response.UserResponse;
import com.blackswandata.usertask.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService service) {this.userService = service;}

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest user){
        UserResponse response = null;
        try {
            response = userService.createUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long id, UserRequest request) {
        UserResponse response = null;
        try {
            response = userService.updateUser(id, request);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listAllUsers() {
        try {
            return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> fingUserById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(userService.getUser(id),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
