package com.aurea.deadcode;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(final String[] args) throws Exception {
        new SpringApplicationBuilder(Application.class).build().run(args);
    }
}