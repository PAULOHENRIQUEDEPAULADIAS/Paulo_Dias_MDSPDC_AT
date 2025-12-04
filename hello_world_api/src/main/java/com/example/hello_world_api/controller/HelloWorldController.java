package com.example.hello_world_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/hello_world")
class HelloWorldController {

    private final WebClient webClient;

    public HelloWorldController(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    @GetMapping
    public String helloWorld() {

        String helloURL = System.getProperty("HELLO_API_URL");
        String worldURL = System.getProperty("WORLD_API_URL");

        String hello = webClient.get().uri(helloURL).retrieve().bodyToMono(String.class).block();
        String world = webClient.get().uri(worldURL).retrieve().bodyToMono(String.class).block();

        return hello + world;
    }
}
