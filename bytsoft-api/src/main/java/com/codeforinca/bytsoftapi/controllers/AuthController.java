package com.codeforinca.bytsoftapi.controllers;


import com.codeforinca.bytsoftapi.auth.JwtTokenBuilder;
import com.codeforinca.bytsoftapi.exceptions.AuthorizationException;
import com.codeforinca.bytsoftapi.models.request.UserModelRequest;
import com.codeforinca.bytsoftapi.models.response.ApiResponse;
import com.codeforinca.bytsoftapi.persistence.entites.User;
import com.codeforinca.bytsoftapi.services.impl.IUserService;
import com.codeforinca.bytsoftapi.services.impl.cache.IRedisCacheService;
import lombok.RequiredArgsConstructor;
import net.projectmonkey.object.mapper.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
       String email = userModelRequest.getEmail();
       String password = userModelRequest.getPassword();
       Map<String,Object> offlineCaptchaMap = userModelRequest.getOfflineCaptcha();

       if (email == null || password == null || offlineCaptchaMap == null)
       {
           return new ResponseEntity<>(
                   new ApiResponse("Username or pas sword or textCode is null")
                   , HttpStatus.BAD_REQUEST
           );
       }

        ApiResponse response = userService.findByEmailAndPassword(email, password);
        if (response.getStatus() == HttpStatus.OK)
        {
            Object responseMap =  userService.checkOfflineCaptcha(offlineCaptchaMap);
            ObjectMapper objectMapper = new ObjectMapper();
            if (responseMap.toString().contains("true")) {
                // save token to redis
                User user = (User) response.getData();
                Map<String, Object> tokens = (Map<String, Object>) redisCacheService.get("#tokens");
                tokens.put(user.getUserName(), response.getToken());

                redisCacheService.set("#tokens", tokens);
            }else{
                response.setStatus(HttpStatus.UNAUTHORIZED);
                response.setMessage("Offline Captcha not okey");
                response.setToken(null);
               return  new ResponseEntity<>(response,response.getStatus());
            }
        }


        return new ResponseEntity<>(response, response.getStatus());
    }


    @PostMapping("/logout")
    public
    ResponseEntity<ApiResponse>
    logout(
            @RequestBody UserModelRequest modelRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        String bearer = request.getHeader("Authorization");
        String token = bearer.substring(7);
        String userName = JwtTokenBuilder.getUsernameFromToken(token);
        User user = userService.findByUsername(userName);
        if (user==null)
        {
            throw new AuthorizationException("Token is not null");
        }

        Map<String,Object> tokens = (Map<String, Object>) redisCacheService.get("#tokens");
        if (tokens.get(user.getUserName())!=null)
        {
            tokens.remove(user.getUserName());
            redisCacheService.set("#tokens",tokens);
        }

        return new ResponseEntity<>(new ApiResponse("OK"),HttpStatus.OK);
    }


    @GetMapping("/offlineCaptcha")
    public
    ResponseEntity<Object>
    offlineCaptcha(

    ){
        return new ResponseEntity<>(userService.offlineCaptcha(),HttpStatus.OK);
    }

}
