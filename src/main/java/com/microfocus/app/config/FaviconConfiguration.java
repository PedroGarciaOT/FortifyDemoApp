package com.microfocus.app.config;

import com.microfocus.app.FortifyDemoApp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class FaviconConfiguration {

    private static final Logger log = LogManager.getLogger(FortifyDemoApp.class);

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Bean("CustomFaviconHandlerMapping")
    public SimpleUrlHandlerMapping faviconHandlerMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Integer.MIN_VALUE);
        mapping.setUrlMap(Collections.singletonMap(contextPath + "/img/favicons/favicon.png",
                faviconRequestHandler()));
        return mapping;
    }

    @Bean("CustomFaviconRequestHandler")
    protected ResourceHttpRequestHandler faviconRequestHandler() {
        ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
        requestHandler.setLocations(Arrays
                .<Resource> asList(new ClassPathResource("/")));
        return requestHandler;
    }

}
