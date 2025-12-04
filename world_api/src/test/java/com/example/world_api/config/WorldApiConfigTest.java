package com.example.world_api.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

class WorldApiConfigTest {

    @Test
    void webClientBeanShouldBeCreated() {
        WorldApiConfig config = new WorldApiConfig();
        WebClient webClient = config.webClient();
        assertThat(webClient).isNotNull();
        // Adicionalmente, verifique se é uma instância padrão sem customizações específicas
        assertThat(webClient).isInstanceOf(WebClient.class);
    }
}