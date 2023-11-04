package com.codeforinca.bytsoftapi.auth;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public
class
AuthanticationServletFilterConfig
    implements
        WebMvcConfigurer
{

    @Override
    public void addInterceptors(
            InterceptorRegistry registry
    ) {
        registry.addInterceptor(new AuthanticationServletFilter()).excludePathPatterns("/auth/**");
    }
}
