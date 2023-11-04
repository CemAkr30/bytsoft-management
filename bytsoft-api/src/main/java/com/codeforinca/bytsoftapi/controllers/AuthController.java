package com.codeforinca.bytsoftapi.controllers;


import com.codeforinca.bytsoftapi.models.request.UserModelRequest;
import com.codeforinca.bytsoftapi.models.response.ApiResponse;
import com.codeforinca.bytsoftapi.services.impl.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {


    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserModelRequest userModelRequest) {
        return new ResponseEntity<>(userService.login(userModelRequest), HttpStatus.OK);
    }
}
