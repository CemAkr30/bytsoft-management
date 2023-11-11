package com.codeforinca.bytsoftapi.services.impl;


import com.codeforinca.bytsoftapi.models.request.UserModelRequest;
import com.codeforinca.bytsoftapi.models.response.ApiResponse;
import com.codeforinca.bytsoftapi.persistence.entites.User;

import java.util.Map;

public interface IUserService
{
    User findByUsername(
        String username
    );

    ApiResponse findByEmailAndPassword(
        String email,String password
    ) throws Exception;

    Map<String,Object> offlineCaptcha(

    );


    Object checkOfflineCaptcha(
            Map<String,Object> args
    );

    UserModelRequest authorizationModuls(
            String userName
    );

}

