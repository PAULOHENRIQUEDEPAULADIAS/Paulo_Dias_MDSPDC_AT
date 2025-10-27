package com.example.world_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class WorldApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorldApiApplication.class, args);
	}

}

@RestController
@RequestMapping("world")
class WorldController{

	@GetMapping
	public String world(){
		return "World!";
	}
}