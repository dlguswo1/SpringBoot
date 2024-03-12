package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    private String resourcePath = "/upload/**"; // view에서 접근할 경로
    private String savePath = "file:///usr/project/java/spring_img/";
//    private String savePath = "file:///home/ubuntu/app/spring_img";
//    private String savePath = "file:///C:/spring_img/"; // 실제 파일 저장 경로

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }

    // 발생할 수 있는 오류를 최대한 방지하기 위해
    // cors 대한 설정 크로스 오리진 리소스 약자
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // local3000에 대해서 다 밑에 요청들을 받을 수 있도록 하겟다.
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}