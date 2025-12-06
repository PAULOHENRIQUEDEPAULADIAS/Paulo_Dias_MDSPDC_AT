package com.example.hello_world_api.controller;

import com.example.hello_world_api.service.HelloWorldService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hello_world")
public class HelloWorldController {

    private final HelloWorldService service;

    public HelloWorldController(HelloWorldService service) {
        this.service = service;
    }

    @GetMapping
    public Mono<String> helloWorld() {
        return Mono.zip(
                service.fetchHello(),
                service.fetchWorld()
        ).map(tuple -> tuple.getT1() + tuple.getT2());
    }
}
