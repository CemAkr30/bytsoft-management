package com.codeforinca.bytsoftapi.auth;

import com.codeforinca.bytsoftapi.exceptions.AuthorizationException;
import com.codeforinca.bytsoftapi.exceptions.ForbidenException;
import com.codeforinca.bytsoftapi.persistence.entites.User;
import com.codeforinca.bytsoftapi.persistence.repository.IUserRepository;
import com.codeforinca.bytsoftapi.services.impl.cache.IRedisCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationServletFilter
        implements HandlerInterceptor {

    private final IUserRepository userRepository;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.addHeader("Access-Control-Allow-Origin", "http://25.64.169.195:3000");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Allow-Credentials", "true");

        String bearer = request.getHeader("Authorization");

        if (bearer == null || !bearer.startsWith("Bearer ")) {
            throw new AuthorizationException("Authorization token is missing or invalid");
        }

        try {
            String token = bearer.substring(7);
            String userName = JwtTokenBuilder.getUsernameFromToken(token);
            User user = userRepository.findByUserName(userName);

            if (user != null) {
                if (!(JwtTokenBuilder.validateToken(token) && !(JwtTokenBuilder.isTokenExpired(token)))) {
                    throw new ForbidenException("Forbidden error");
                }
                return true;
            }

            throw new AuthorizationException("Authorization token is missing or invalid");
        }catch (Exception e){
            throw new AuthorizationException("Authorization token is missing or invalid");
        }
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
