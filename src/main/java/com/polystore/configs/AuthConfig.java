package com.polystore.configs;

import com.polystore.interceptors.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(
                        "/account/change-password", // Hai chức năng trong mục 3[cite: 2]
                        "/account/edit-profile", 
                        "/order/**",                // Toàn bộ các chức năng trong mục 4[cite: 2]
                        "/admin/**"                 // Công cụ quản trị của admin[cite: 3]
                )
                .excludePathPatterns("/auth/login", "/home/index"); // Ngoại trừ những trang public
    }
}