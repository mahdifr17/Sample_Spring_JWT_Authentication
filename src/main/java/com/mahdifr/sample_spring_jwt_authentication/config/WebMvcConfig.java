package com.mahdifr.sample_spring_jwt_authentication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfig
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 3600;

    /** 
     * Configuration to allow Cross Origin Resource Sharing
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods(
                "HEAD", 
                "OPTIONS",
                "GET",
                "POST",
                "PUT",
                "PATCH",
                "DELETE")
            .maxAge(MAX_AGE_SECS);
    }
}