package com.codeforinca.bytsoftapi.services.clazz;


import com.codeforinca.bytsoftapi.models.request.UserModelRequest;
import com.codeforinca.bytsoftapi.models.response.ApiResponse;
import com.codeforinca.bytsoftapi.persistence.repository.IUserRepository;
import com.codeforinca.bytsoftapi.services.impl.IUserService;
import com.codeforinca.bytsoftapi.services.impl.cache.IRedisCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl
            implements IUserService
{

    private final IUserRepository userRepository;
    private final IRedisCacheService redisCacheService;

    @Override
    public Object findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public ApiResponse login(UserModelRequest userModelRequest) {
        return null;
    }
}
