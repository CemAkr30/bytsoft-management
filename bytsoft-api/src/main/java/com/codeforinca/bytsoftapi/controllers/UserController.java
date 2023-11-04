package com.codeforinca.bytsoftapi.controllers;


import com.codeforinca.bytsoftapi.services.impl.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController
{

    private final IUserService userService;

   /* @GetMapping("/test")
    public ResponseEntity<Object> test(@RequestBody String username){
        return new ResponseEntity<>(userService.findByUsername(username),OK);
    }*/

}
