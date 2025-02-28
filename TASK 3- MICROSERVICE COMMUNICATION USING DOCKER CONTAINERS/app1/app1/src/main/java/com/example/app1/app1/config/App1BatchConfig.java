package com.example.app1.app1.config;

import com.example.app1.app1.entity.User;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class App1BatchConfig {

    @Bean
    public JsonItemReader<User> jsonItemReader() {
        return new JsonItemReaderBuilder<User>()
                .name("userItemReader")
                .resource(new ClassPathResource("users.json"))
                .jsonObjectReader(new JacksonJsonObjectReader<>(User.class))
                .build();
    }
}