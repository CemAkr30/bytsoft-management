package com.codeforinca.bytsoftapi.auth;

import com.codeforinca.bytsoftapi.services.impl.cache.IRedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public
class
AuthanticationServletFilter
    implements
        HandlerInterceptor
{


    @Autowired
    private IRedisCacheService redisCacheService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    )  {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String token = cookie.getValue();
                if (token != null) {
                    Object user = redisCacheService.get(token);
                    if (user != null) {
                        return true;
                    }
                }
            }
        }

        return true;
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
