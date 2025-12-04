package com.example.hello_api.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

class WebClientConfigTest {

    @Test
    void shouldCreateDbWebClient() {
        WebClientConfig config = new WebClientConfig();
        WebClient client = config.dbWebClient();

        assertNotNull(client);
    }
}
