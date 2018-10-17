package com.aurea.bellerophon;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.aurea")
@EnableAutoConfiguration
public class Application {

    public static void main(final String[] args) {
        new SpringApplicationBuilder(Application.class).build().run(args);
    }
}