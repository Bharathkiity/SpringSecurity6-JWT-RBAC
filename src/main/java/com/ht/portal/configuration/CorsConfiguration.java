package com.ht.portal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("http://localhost:4200") // Allowed pattern for dynamic matching
                        .allowedMethods("*")  // Allow all methods (GET, POST, etc.)
                        .allowedHeaders("*")  // Allow all headers
                        .exposedHeaders("Authorization", "Content-Type") // Expose these headers
                        .allowCredentials(true)  // Allow credentials (cookies, etc.)
                        .maxAge(3600);  // Cache for 1 hour
            }
        };
    }
}
