package com.example.world_api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WorldLogService {

    private final WebClient webClient;

    public WorldLogService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Void> logCall(String response) {
        return webClient.post()
                .uri("http://localhost:8080/logs?service=world_api&response=" + response)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(err -> {
                    System.err.println("Erro ao enviar log: " + err.getMessage());
                    return Mono.empty();
                });
    }
}
