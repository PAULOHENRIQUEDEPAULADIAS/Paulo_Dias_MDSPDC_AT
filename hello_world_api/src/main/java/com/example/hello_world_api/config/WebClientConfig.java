package com.example.hello_world_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${HELLO}")
    private String helloApiUrl;

    @Value("${WORLD}")
    private String worldApiUrl;

    @Bean
    public WebClient helloClient() {
        return WebClient.builder()
                .baseUrl(helloApiUrl)
                .build();
    }

    @Bean
    public WebClient worldClient() {
        return WebClient.builder()
                .baseUrl(worldApiUrl)
                .build();
    }
}
