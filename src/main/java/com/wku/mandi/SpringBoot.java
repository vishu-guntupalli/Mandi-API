package com.wku.mandi;

import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    
    
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
   	MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
   	ObjectMapper objectMapper = new ObjectMapper();
   	objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
   	jsonConverter.setObjectMapper(objectMapper);
   	return jsonConverter;
    }
    
}
