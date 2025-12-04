package com.example.world_api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class WorldLogServiceTest {

    private WebClient mockClient;
    private WebClient.RequestBodyUriSpec mockUriSpec;
    private WebClient.RequestBodySpec mockBodySpec;
    private WebClient.ResponseSpec mockResponseSpec;

    private WorldLogService service;

    @BeforeEach
    void setup() {
        mockClient = mock(WebClient.class);

        mockUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        mockBodySpec = mock(WebClient.RequestBodySpec.class);
        mockResponseSpec = mock(WebClient.ResponseSpec.class);

        // Encadeamento para POST sem body
        when(mockClient.post()).thenReturn(mockUriSpec);
        when(mockUriSpec.uri(anyString())).thenReturn(mockBodySpec);
        when(mockBodySpec.retrieve()).thenReturn(mockResponseSpec);

        service = new WorldLogService(mockClient);
    }

    @Test
    void shouldLogCallSuccessfully() {
        when(mockResponseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        StepVerifier.create(service.logCall("Test Response"))
                .verifyComplete();

        verify(mockClient).post();
        verify(mockUriSpec).uri(argThat((String str) -> str.equals("http://localhost:8080/logs?service=world_api&response=Test Response")));
        verify(mockBodySpec).retrieve();
    }

    @Test
    void shouldHandleErrorsAndReturnEmptyMono() {
        when(mockResponseSpec.bodyToMono(Void.class))
                .thenReturn(Mono.error(new RuntimeException("Test error")));

        StepVerifier.create(service.logCall("Error Response"))
                .verifyComplete(); // Erro é engolido e retorna empty

        verify(mockClient).post();
        verify(mockUriSpec).uri(argThat((String str) -> str.equals("http://localhost:8080/logs?service=world_api&response=Error Response")));
        verify(mockBodySpec).retrieve();
    }
}