package com.example.hello_world_api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class HelloWorldServiceTest {

    private WebClient helloClient;
    private WebClient worldClient;
    private WebClient.RequestHeadersUriSpec<?> uriSpec;
    private WebClient.RequestHeadersSpec<?> headersSpec;
    private WebClient.ResponseSpec responseSpec;

    private HelloWorldService service;

    @BeforeEach
    void setup() {
        helloClient = mock(WebClient.class);
        worldClient = mock(WebClient.class);

        uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        headersSpec = mock(WebClient.RequestHeadersSpec.class);
        responseSpec = mock(WebClient.ResponseSpec.class);

        when(helloClient.get()).thenReturn((WebClient.RequestHeadersUriSpec) uriSpec);
        when(worldClient.get()).thenReturn((WebClient.RequestHeadersUriSpec) uriSpec);

        when(uriSpec.retrieve()).thenReturn(responseSpec);

        service = new HelloWorldService(helloClient, worldClient);
    }

    @Test
    void fetchHelloShouldReturnValue() {
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("Hello "));

        StepVerifier.create(service.fetchHello())
                .expectNext("Hello ")
                .verifyComplete();
    }

    @Test
    void fetchWorldShouldFallbackOnError() {
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("fail")));

        StepVerifier.create(service.fetchWorld())
                .expectNext("ErrorWorld") // depende do seu fallback
                .verifyComplete();
    }
}
