package com.carrot.habbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HabbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabbitApplication.class, args);
	}
}
