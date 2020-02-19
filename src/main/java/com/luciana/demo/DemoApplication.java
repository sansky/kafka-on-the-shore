package com.luciana.demo;


import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer{

    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws InterruptedException, IOException {

        log.info("START APPLICATION");
        ConfigurableApplicationContext context = new SpringApplicationBuilder(DemoApplication.class)
                .web(WebApplicationType.NONE).run(args);

    }
}
