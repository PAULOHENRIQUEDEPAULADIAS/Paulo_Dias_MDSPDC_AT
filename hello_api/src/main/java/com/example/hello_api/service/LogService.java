package com.example.hello_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class LogService {

    private final WebClient dbClient;
    private final Logger logger = LoggerFactory.getLogger(LogService.class);

    public LogService(WebClient dbWebClient) {
        this.dbClient = dbWebClient;
    }

    /**
     * Envia o log para o db_service via query params.
     * Retorna um Mono<Void> que representa a conclusão da chamada (pode ser ignorado pelo chamador).
     */
    public Mono<Void> sendLog(String serviceName, String response) {
        // inclui timestamp opcional como string (se db_service não aceitar timestamp via param, remova)
        String timestamp = LocalDateTime.now().toString();

        return dbClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/logs")
                        .queryParam("service", serviceName)
                        .queryParam("response", response)
                        .build())
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(e -> logger.warn("Falha ao enviar log para db_service: {}", e.toString()))
                .onErrorResume(e -> Mono.empty()); // swallow errors — não impacta endpoint principal
    }
}
