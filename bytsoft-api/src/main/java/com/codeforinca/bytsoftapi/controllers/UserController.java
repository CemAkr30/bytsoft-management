package com.codeforinca.bytsoftapi.controllers;


import com.codeforinca.bytsoftapi.auth.JwtTokenBuilder;
import com.codeforinca.bytsoftapi.models.response.ApiResponse;
import com.codeforinca.bytsoftapi.services.impl.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController
{

    private final IUserService userService;

    @GetMapping(name = "/authModules")
    public
    ResponseEntity<ApiResponse>
    authModules(
            @RequestHeader("Authorization") String token
    ){
        return new ResponseEntity<>(
                new ApiResponse(
                        "OK",
                        userService.authorizationModuls(
                                JwtTokenBuilder.generateToken(
                                        token
                                )
                        )
                ),
                HttpStatus.OK
        );
    }
}
