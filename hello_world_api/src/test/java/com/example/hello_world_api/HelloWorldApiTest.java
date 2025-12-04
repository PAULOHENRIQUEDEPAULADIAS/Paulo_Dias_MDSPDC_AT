package com.example.hello_world_api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HelloWorldApiTest {

    private static GenericContainer<?> helloApi;
    private static GenericContainer<?> worldApi;
    private static GenericContainer<?> helloWorldApi;

    @BeforeAll
    static void init() {

        // Caminhos absolutos dos JARs
        String helloJar = Paths.get("../hello_api/target/hello_api-0.0.1-SNAPSHOT.jar").toAbsolutePath().toString();
        String worldJar = Paths.get("../world_api/target/world_api-0.0.1-SNAPSHOT.jar").toAbsolutePath().toString();
        String helloWorldJar = Paths.get("target/hello_world_api-0.0.1-SNAPSHOT.jar").toAbsolutePath().toString();

        // ------------- HELLO API --------------
        helloApi = new GenericContainer<>(
                new ImageFromDockerfile()
                        .withFileFromPath("app.jar", Paths.get(helloJar))
                        .withDockerfileFromBuilder(builder -> builder
                                .from("eclipse-temurin:21-jdk")
                                .copy("app.jar", "/app/app.jar")
                                .workDir("/app")
                                .expose(8081)
                                .cmd("java", "-jar", "app.jar")
                                .build()
                        )
        ).withExposedPorts(8081);

        // ------------- WORLD API --------------
        worldApi = new GenericContainer<>(
                new ImageFromDockerfile()
                        .withFileFromPath("app.jar", Paths.get(worldJar))
                        .withDockerfileFromBuilder(builder -> builder
                                .from("eclipse-temurin:21-jdk")
                                .copy("app.jar", "/app/app.jar")
                                .workDir("/app")
                                .expose(8082)
                                .cmd("java", "-jar", "app.jar")
                                .build()
                        )
        ).withExposedPorts(8082);

        // ------------- HELLO WORLD API --------------
        helloWorldApi = new GenericContainer<>(
                new ImageFromDockerfile()
                        .withFileFromPath("app.jar", Paths.get(helloWorldJar))
                        .withDockerfileFromBuilder(builder -> builder
                                .from("eclipse-temurin:21-jdk")
                                .copy("app.jar", "/app/app.jar")
                                .workDir("/app")
                                .expose(8080)
                                .cmd("java", "-jar", "app.jar")
                                .build()
                        )
        ).withExposedPorts(8080);

        // Start containers
        helloApi.start();
        worldApi.start();
        helloWorldApi.start();
    }

    @Test
    void testHelloWorldIntegration() {
        RestTemplate rest = new RestTemplate();

        String url = "http://localhost:" + helloWorldApi.getMappedPort(8080) + "/hello_world";

        String response = rest.getForObject(url, String.class);

        assertThat(response).isEqualTo("Hello World");
    }
}
