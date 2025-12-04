package com.example.hello_api;

import com.example.hello_api.service.LogService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class HelloApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(HelloApiApplication.class, args);
	}
}

@RestController
@RequestMapping("/hello")
class HelloController {

	private final LogService logService;

	public HelloController(LogService logService) {
		this.logService = logService;
	}

	@GetMapping
	public Mono<String> hello() {
		String response = "Hello";
		// Fire-and-forget: dispara o envio do log em background
		logService.sendLog("hello_api", response).subscribe(
				null,
				err -> { /* já tratado em LogService, mas pode logar extra se quiser */ }
		);
		return Mono.just(response);
	}
}
