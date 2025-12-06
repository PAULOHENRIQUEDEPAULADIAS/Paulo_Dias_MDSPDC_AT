package com.example.world_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${db.service.url}")  // Permite override via env/properties; mude para db_service no Docker
    private String dbServiceUrl;

    @Bean
    public WebClient dbWebClient() {
        return WebClient.builder()
                .baseUrl(dbServiceUrl)
                .build();
    }
}