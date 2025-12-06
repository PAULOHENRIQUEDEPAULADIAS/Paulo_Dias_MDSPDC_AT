package com.example.hello_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;  // Importe isso
import org.slf4j.Logger;  // Importe
import org.slf4j.LoggerFactory;  // Importe

@Configuration
public class WebClientConfig {

    @Value("${db.service.url}")
    private String dbServiceUrl;

    private static final Logger logger = LoggerFactory.getLogger(WebClientConfig.class);  // Adicione isso

    @PostConstruct  // Adicione isso
    public void init() {
        logger.info("DB_SERVICE_URL injetada: {}", dbServiceUrl);  // Isso vai printar nos logs o valor real
    }

    @Bean
    public WebClient dbWebClient() {
        return WebClient.builder()
                .baseUrl(dbServiceUrl)
                .build();
    }

}
