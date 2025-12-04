package com.example.world_api;

import com.example.world_api.service.WorldLogService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class WorldApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorldApiApplication.class, args);
	}

}

@RestController
@RequestMapping("world")
class WorldController {

	private final WorldLogService logService;

	public WorldController(WorldLogService logService) {
		this.logService = logService;
	}

	@GetMapping
	public Mono<String> world() {
		String response = "World!";
		return logService.logCall(response)
				.then(Mono.just(response));
	}
}