package com.codeforinca.bytsoftapi.controllers;


import com.codeforinca.bytsoftapi.models.request.UserModelRequest;
import com.codeforinca.bytsoftapi.models.response.ApiResponse;
import com.codeforinca.bytsoftapi.persistence.entites.User;
import com.codeforinca.bytsoftapi.services.impl.IUserService;
import com.codeforinca.bytsoftapi.services.impl.cache.IRedisCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class
AuthController
{
    private final IUserService userService;
    private final IRedisCacheService redisCacheService;

    @PostMapping("/login")
    public
    ResponseEntity<ApiResponse>
    login(
            @RequestBody UserModelRequest userModelRequest,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) throws Exception {
       String userName = userModelRequest.getUserName();
       String password = userModelRequest.getPassword();

       if (userName == null || password == null)
       {
           return new ResponseEntity<>(new ApiResponse("Username or password is null"), HttpStatus.BAD_REQUEST);
       }

        ApiResponse response = userService.findByUserNameAndPassword(userName, password);
        if (response.getStatus() == HttpStatus.OK)
        {
            // save token to redis
            User user = (User) response.getData();
            Map<String,Object> tokens = (Map<String, Object>) redisCacheService.get("#tokens");
            tokens.put(user.getUserName(), response.getToken());

            redisCacheService.set("#tokens", tokens);

            Cookie cookie = new Cookie("token", response.getToken());
            httpServletResponse.addCookie(cookie);
        }


        return new ResponseEntity<>(response, response.getStatus());
    }
}
