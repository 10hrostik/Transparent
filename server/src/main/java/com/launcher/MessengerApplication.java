package com.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableR2dbcRepositories("com.api.repositories")
@ComponentScan(basePackages = {"com.api"})
public class MessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessengerApplication.class, args);
	}

}
