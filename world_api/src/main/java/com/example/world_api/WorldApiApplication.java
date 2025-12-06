package com.example.world_api;

import com.example.world_api.service.LogService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class WorldApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(WorldApiApplication.class, args);
	}
}

@RestController
@RequestMapping("/world")  // Adicionado / para consistência
class WorldController {

	private final LogService logService;

	public WorldController(LogService logService) {
		this.logService = logService;
	}

	@GetMapping
	public Mono<String> world() {
		String response = "World";  // Padronizado sem !, mas adicione se quiser
		// Fire-and-forget: dispara o envio do log em background
		logService.sendLog("world_api", response).subscribe(
				null,
				err -> { /* já tratado em LogService, mas pode logar extra se quiser */ }
		);
		return Mono.just(response);
	}
}