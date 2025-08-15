package com.example.demo;

import com.google.cloud.firestore.Firestore;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestConfig {

    @Bean
    @Primary
    public Firestore firestore() {
        return Mockito.mock(Firestore.class);
    }
}
