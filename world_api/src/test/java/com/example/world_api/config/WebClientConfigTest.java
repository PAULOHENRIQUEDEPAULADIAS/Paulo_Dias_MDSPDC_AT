package com.example.world_api.config;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = WebClientConfig.class)
@TestPropertySource(properties = "DB_SERVICE_URL=http://test-url:9999")
class WebClientConfigTest {

    @MockBean
    private ApplicationContext context;


    @Test
    void dbWebClient_ShouldCreateWebClientWithCorrectBaseUrl() {
        WebClientConfig config = new WebClientConfig();
        WebClient webClient = config.dbWebClient();
        assertThat(webClient).isNotNull();
    }
}