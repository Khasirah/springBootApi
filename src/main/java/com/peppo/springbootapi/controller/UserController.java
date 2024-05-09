package com.peppo.springbootapi.controller;

import com.peppo.springbootapi.entity.User;
import com.peppo.springbootapi.model.RegisterUserRequest;
import com.peppo.springbootapi.model.UpdateUserRequest;
import com.peppo.springbootapi.model.UserResponse;
import com.peppo.springbootapi.model.WebResponse;
import com.peppo.springbootapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(
            path = "/api/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@RequestBody RegisterUserRequest registerUserRequest) {
        userService.register(registerUserRequest);
        return WebResponse.<String>builder().data("Username berhasil terdaftar").build();
    }

    @GetMapping(
            path = "/api/users/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> get(User user) {
        UserResponse userResponse = userService.get(user);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }

    @PatchMapping(
            path = "/api/users/current",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> update(User user, @RequestBody UpdateUserRequest updateUserRequest) {
        UserResponse response = userService.update(user, updateUserRequest);
        return WebResponse.<UserResponse>builder().data(response).build();
    }

}
