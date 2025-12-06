package com.example.world_api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogServiceTest {

    @Mock
    private WebClient dbClient;

    @Mock
    private Logger logger;

    @InjectMocks
    private LogService logService;

    @Test
    void sendLog_ShouldSendPostRequestSuccessfully() {
        // Arrange
        WebClient.RequestBodyUriSpec requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(dbClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = logService.sendLog("test_service", "test_response");

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        // Captura e verifica a Function passada para uri
        ArgumentCaptor<Function<UriBuilder, URI>> captor = ArgumentCaptor.forClass(Function.class);
        verify(requestBodyUriSpec).uri(captor.capture());
        Function<UriBuilder, URI> capturedFunction = captor.getValue();

        // Cria um mock UriBuilder para aplicar a function e verificar chamadas
        UriBuilder mockUriBuilder = mock(UriBuilder.class);
        when(mockUriBuilder.path(anyString())).thenReturn(mockUriBuilder);
        when(mockUriBuilder.queryParam(anyString(), Optional.ofNullable(any()))).thenReturn(mockUriBuilder);
        when(mockUriBuilder.build()).thenReturn(URI.create("http://dummy"));  // Retorno dummy para apply não falhar

        capturedFunction.apply(mockUriBuilder);

        // Verifica as chamadas no UriBuilder
        verify(mockUriBuilder).path("/logs");
        verify(mockUriBuilder).queryParam("service", "test_service");
        verify(mockUriBuilder).queryParam("response", "test_response");
        verify(mockUriBuilder).queryParam(eq("timestamp"), Optional.ofNullable(argThat(timestamp -> timestamp instanceof String && !((String) timestamp).isEmpty())));

        verifyNoInteractions(logger);  // Sem erro, sem warn
    }
}