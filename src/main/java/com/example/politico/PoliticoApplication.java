package com.example.politico;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.Properties;

@SpringBootApplication

public class PoliticoApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure().load();
        SpringApplication app = new SpringApplication(PoliticoApplication.class);
        app.setDefaultProperties(Collections.singletonMap("spring.datasource.url", dotenv.get("DB_URL")));
        app.setDefaultProperties(Collections.singletonMap("spring.datasource.username", dotenv.get("DB_USER")));
        app.setDefaultProperties(Collections.singletonMap("spring.datasource.password", dotenv.get("DB_PASS")));
        app.run(args);

    }

}
