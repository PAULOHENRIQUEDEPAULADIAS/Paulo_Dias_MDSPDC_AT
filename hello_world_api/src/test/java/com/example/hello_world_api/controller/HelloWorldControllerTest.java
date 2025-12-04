package com.example.hello_world_api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class HelloWorldControllerTest {

    private WebClient mockWebClient;
    private WebClient.RequestHeadersUriSpec mockUriSpec;
    private WebClient.RequestHeadersSpec mockHeadersSpec;
    private WebClient.ResponseSpec mockResponseSpec;

    private HelloWorldController controller;

    @BeforeEach
    void setup() {
        WebClient.Builder mockBuilder = mock(WebClient.Builder.class);
        mockWebClient = mock(WebClient.class);

        when(mockBuilder.build()).thenReturn(mockWebClient);

        mockUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        mockHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        mockResponseSpec = mock(WebClient.ResponseSpec.class);

        when(mockWebClient.get()).thenReturn(mockUriSpec);
        when(mockUriSpec.uri(anyString())).thenReturn(mockHeadersSpec);
        when(mockHeadersSpec.retrieve()).thenReturn(mockResponseSpec);

        controller = new HelloWorldController(mockBuilder);

        // Mock system properties for URLs
        System.setProperty("HELLO_API_URL", "http://hello.api");
        System.setProperty("WORLD_API_URL", "http://world.api");
    }

    @Test
    void shouldReturnHelloWorldSuccessfully() {
        when(mockResponseSpec.bodyToMono(String.class)).thenReturn(Mono.just("Hello "))
                .thenReturn(Mono.just("World!"));

        String result = controller.helloWorld();

        verify(mockWebClient, times(2)).get();
        verify(mockUriSpec).uri("http://hello.api");
        verify(mockUriSpec).uri("http://world.api");
        verify(mockHeadersSpec, times(2)).retrieve();

        assertThat(result).isEqualTo("Hello World!");
    }

    @Test
    void shouldHandleErrorsGracefully() {
        when(mockResponseSpec.bodyToMono(String.class)).thenReturn(Mono.just("Hello "))
                .thenReturn(Mono.error(new RuntimeException("World error")));

        assertThrows(RuntimeException.class, () -> controller.helloWorld());
    }
}