package com.virucccc.converter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/signin").setViewName("signin");
        registry.addViewController("/signup").setViewName("signup");
        registry.addViewController("/converter").setViewName("converter");
        registry.addViewController("/").setViewName("redirect:/converter");
    }
}
