package com.example.hello_api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LogServiceTest {

    private WebClient mockClient;
    private WebClient.RequestBodyUriSpec mockUriSpec;
    private WebClient.RequestBodySpec mockBodySpec;
    private WebClient.ResponseSpec mockResponseSpec;

    private LogService service;

    @BeforeEach
    void setup() {
        mockClient = mock(WebClient.class);

        mockUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        mockBodySpec = mock(WebClient.RequestBodySpec.class);
        mockResponseSpec = mock(WebClient.ResponseSpec.class);

        // Encadeamento corrigido (sem bodyValue, já que não é chamado):
        when(mockClient.post()).thenReturn(mockUriSpec);
        when(mockUriSpec.uri(any(Function.class))).thenReturn(mockBodySpec);
        when(mockBodySpec.retrieve()).thenReturn(mockResponseSpec);

        service = new LogService(mockClient);
    }

    @Test
    void shouldSendLogSuccessfully() {
        when(mockResponseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        StepVerifier.create(service.sendLog("hello_api", "OK"))
                .verifyComplete();

        verify(mockClient).post();
        verify(mockUriSpec).uri(any(Function.class));
        verify(mockBodySpec).retrieve();
    }

    @Test
    void shouldHandleErrorsAndReturnEmptyMono() {
        when(mockResponseSpec.bodyToMono(Void.class))
                .thenReturn(Mono.error(new RuntimeException("teste")));

        StepVerifier.create(service.sendLog("hello_api", "FAIL"))
                .verifyComplete(); // erro engolido

        verify(mockClient).post();
        verify(mockUriSpec).uri(any(Function.class));
        verify(mockBodySpec).retrieve();
    }
}