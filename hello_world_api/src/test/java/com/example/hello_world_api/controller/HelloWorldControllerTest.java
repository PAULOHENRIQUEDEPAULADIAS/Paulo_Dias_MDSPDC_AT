package com.example.hello_world_api.controller;

import com.example.hello_world_api.service.HelloWorldService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class HelloWorldControllerTest {

    private HelloWorldService service;
    private HelloWorldController controller;

    @BeforeEach
    void setup() {
        service = mock(HelloWorldService.class);
        controller = new HelloWorldController(service);
    }

    @Test
    void shouldReturnHelloWorldSuccessfully() {
        when(service.fetchHello()).thenReturn(Mono.just("Hello "));
        when(service.fetchWorld()).thenReturn(Mono.just("World!"));

        StepVerifier.create(controller.helloWorld())
                .expectNext("Hello World!")
                .verifyComplete();

        verify(service).fetchHello();
        verify(service).fetchWorld();
    }

}
