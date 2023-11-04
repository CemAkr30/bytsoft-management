package com.codeforinca.bytsoftapi.auth;

import com.codeforinca.bytsoftapi.exceptions.AuthorizationException;
import com.codeforinca.bytsoftapi.exceptions.ForbidenException;
import com.codeforinca.bytsoftapi.persistence.entites.User;
import com.codeforinca.bytsoftapi.services.impl.cache.IRedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class AuthenticationServletFilter implements HandlerInterceptor {

    @Autowired
    private IRedisCacheService redisCacheService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.addHeader("Access-Control-Allow-Origin", "http://192.168.217.197:3000");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Allow-Credentials", "true");

        String bearer = request.getHeader("Authorization");

        if (bearer == null || !bearer.startsWith("Bearer ")) {
            throw new AuthorizationException("Authorization token is missing or invalid");
        }

        String token = bearer.substring(7);
        String userName = JwtTokenBuilder.getUsernameFromToken(token);
        Map<String, Object> tokens = (Map<String, Object>) redisCacheService.get("#tokens");
        User user = (User) tokens.get(userName);

        if (user != null) {
            if (!(JwtTokenBuilder.validateToken(token) && JwtTokenBuilder.isTokenExpired(token))) {
                throw new ForbidenException("Forbidden error");
            }
            return true;
        }

        return false;
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
