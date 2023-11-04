package com.codeforinca.bytsoftapi.controllers;


import com.codeforinca.bytsoftapi.services.impl.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController
{

    private final IUserService userService;

    @GetMapping("/test")
    public ResponseEntity<Object> test(@RequestBody String username){
        return new ResponseEntity<>(userService.findByUsername(username),OK);
    }

}
