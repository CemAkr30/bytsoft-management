package com.codeforinca.bytsoftapi.services.clazz;


import com.codeforinca.bytsoftapi.auth.JwtTokenBuilder;
import com.codeforinca.bytsoftapi.models.request.UserModelRequest;
import com.codeforinca.bytsoftapi.models.response.ApiResponse;
import com.codeforinca.bytsoftapi.persistence.entites.User;
import com.codeforinca.bytsoftapi.persistence.repository.IUserRepository;
import com.codeforinca.bytsoftapi.services.impl.IUserService;
import com.codeforinca.bytsoftapi.services.impl.cache.IRedisCacheService;
import com.codeforinca.bytsoftcore.utils.AesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserServiceImpl
            implements IUserService
{

    private final IUserRepository userRepository;

    @Override
    public Object findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public ApiResponse findByUserNameAndPassword(String userName, String password) throws Exception {
        User user = userRepository.findByUserName(userName);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.OK);
        if ( user == null )
        {
            apiResponse.setMessage("User not found");
            apiResponse.setStatus(HttpStatus.NOT_FOUND);
            return apiResponse;
        }

        if (!user.getPassword().equals(AesUtils.encrypt(password)))
        {
            apiResponse.setMessage("Password not match");
            apiResponse.setStatus(HttpStatus.NOT_FOUND);
            return apiResponse;
        }

        apiResponse.setData(user);
        apiResponse.setToken(JwtTokenBuilder.generateToken(user.getUserName()));

       return apiResponse;
    }


}
