package com.microfocus.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication()
public class InjectionWebApp extends SpringBootServletInitializer {

    private static final Logger log = LogManager.getLogger(InjectionWebApp.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(InjectionWebApp.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(InjectionWebApp.class);
    }
}
