package com.example.db_service.controller;

import com.example.db_service.model.CallLog;
import com.example.db_service.service.CallLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class CallLogControllerTest {

    private WebTestClient webClient;
    private CallLogService service;

    @BeforeEach
    void setup() {
        service = Mockito.mock(CallLogService.class);
        CallLogController controller = new CallLogController(service);

        webClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void saveLog_ShouldReturnCreatedLog() {
        CallLog log = new CallLog("hello", "Hello");

        Mockito.when(service.saveLog("hello", "Hello"))
                .thenReturn(Mono.just(log));

        webClient.post()
                .uri("/logs?service=hello&response=Hello")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.serviceName").isEqualTo("hello")
                .jsonPath("$.response").isEqualTo("Hello");
    }

    @Test
    void listLogs_ShouldReturnAllLogs() {
        CallLog log = new CallLog("hello", "Hello");

        Mockito.when(service.listLogs())
                .thenReturn(Flux.just(log));

        webClient.get()
                .uri("/logs")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].serviceName").isEqualTo("hello");
    }
}
