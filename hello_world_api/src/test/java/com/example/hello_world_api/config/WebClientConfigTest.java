package com.example.hello_world_api.config;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

class WebClientConfigTest {

    @Test
    void shouldCreateBothClients() {
        WebClientConfig config = new WebClientConfig();

        ReflectionTestUtils.setField(config, "helloApiUrl", "http://hello");
        ReflectionTestUtils.setField(config, "worldApiUrl", "http://world");

        WebClient helloClient = config.helloClient();
        WebClient worldClient = config.worldClient();

        assertThat(helloClient).isNotNull();
        assertThat(worldClient).isNotNull();
        assertThat(helloClient).isInstanceOf(WebClient.class);
        assertThat(worldClient).isInstanceOf(WebClient.class);
    }
}
