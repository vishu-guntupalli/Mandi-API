package com.wku.mandi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by srujangopu on 7/19/15.
 */

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class SpringBoot {


    public static void main(String[] args) {
        SpringApplication.run(SpringBoot.class, args);
    }
}
