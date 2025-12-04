package com.example.hello_world_api.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

class WebClientConfigTest {

    @Test
    void webClientBeanShouldBeCreated() {
        WebClientConfig config = new WebClientConfig();
        WebClient webClient = config.webClient();
        assertThat(webClient).isNotNull();
        assertThat(webClient).isInstanceOf(WebClient.class);
    }
}