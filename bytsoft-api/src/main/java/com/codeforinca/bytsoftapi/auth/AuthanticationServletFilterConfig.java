package com.codeforinca.bytsoftapi.auth;


import com.codeforinca.bytsoftapi.persistence.repository.IUserRepository;
import com.codeforinca.bytsoftapi.services.impl.cache.IRedisCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public
class
AuthanticationServletFilterConfig
    implements
        WebMvcConfigurer
{

    private final IUserRepository userRepository;

    @Override
    public void addInterceptors(
            InterceptorRegistry registry
    ) {
        registry.addInterceptor(new AuthenticationServletFilter(userRepository))
                .excludePathPatterns("/auth/**");
    }
}
