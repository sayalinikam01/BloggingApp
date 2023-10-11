package com.backend.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
         {
            registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("file:C://Users//sanika//OneDrive - SAS//Desktop//BloggingApp//BloggingApp//Backend//src//main//resources//static//uploads//");
        }
    }
}
