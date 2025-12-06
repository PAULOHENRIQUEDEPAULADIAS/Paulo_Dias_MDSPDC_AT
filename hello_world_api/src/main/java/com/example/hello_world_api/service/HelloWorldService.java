package com.example.hello_world_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class HelloWorldService {

    private final WebClient helloClient;
    private final WebClient worldClient;
    private final Logger logger = LoggerFactory.getLogger(HelloWorldService.class);

    public HelloWorldService(WebClient helloClient, WebClient worldClient) {
        this.helloClient = helloClient;
        this.worldClient = worldClient;
    }

    public Mono<String> fetchHello() {
        String fullUri = helloClient.get().uri("/hello").toString();
        logger.info("Full URI for hello: {}", fullUri);
        return helloClient.get()
                .uri("/hello")
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> logger.warn("Erro ao chamar HELLO_API: {}", e.toString()))
                .onErrorResume(e -> Mono.just("Error Hello"));
    }

    public Mono<String> fetchWorld() {
        String fullUri = worldClient.get().uri("/world").toString();
        logger.info("Full URI for world: {}", fullUri);
        return worldClient.get()
                .uri("/world")
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> logger.warn("Erro ao chamar WORLD_API: {}", e.toString()))
                .onErrorResume(e -> Mono.just("Error World"));
    }
}
