package com.example.world_api;

import com.example.world_api.service.WorldLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = WorldController.class)
class WorldControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private WorldLogService logService;

    @Test
    void shouldReturnWorldAndLogSuccessfully() {
        when(logService.logCall("World!")).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/world")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("World!");
    }
}