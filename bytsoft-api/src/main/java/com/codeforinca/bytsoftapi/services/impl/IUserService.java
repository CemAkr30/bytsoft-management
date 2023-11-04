package com.codeforinca.bytsoftapi.services.impl;


import com.codeforinca.bytsoftapi.models.request.UserModelRequest;
import com.codeforinca.bytsoftapi.models.response.ApiResponse;

public interface IUserService
{
    Object findByUsername(
        String username
    );

    ApiResponse login(
       UserModelRequest userModelRequest
    );
}

