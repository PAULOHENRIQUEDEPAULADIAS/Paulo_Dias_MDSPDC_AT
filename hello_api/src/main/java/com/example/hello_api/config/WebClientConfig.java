package com.example.hello_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient dbWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8083") // ajuste se seu db_service usar outra porta
                .build();
    }
}
