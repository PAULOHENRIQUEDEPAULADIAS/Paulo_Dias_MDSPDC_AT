package com.example.hello_world_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class HelloWorldApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldApiApplication.class, args);
	}

}

@RestController
@RequestMapping("hello_world")
class HelloWorld{

	@Autowired
	private DiscoveryClient discoveryClient;

	private final RestTemplate restTemplate = new RestTemplate();

	@GetMapping
	public String hello_world(){

		String helloURL = discoveryClient.getInstances("hello_api").get(0).getUri().toString() + "/hello";
		String worldURL = discoveryClient.getInstances("world_api").get(0).getUri().toString() + "/world";

		String hello = restTemplate.getForObject(helloURL, String.class);
		String world = restTemplate.getForObject(worldURL, String.class);

		return hello + world;
	}

}