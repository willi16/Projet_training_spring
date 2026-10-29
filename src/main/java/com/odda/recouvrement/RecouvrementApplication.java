package com.odda.recouvrement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RecouvrementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecouvrementApplication.class, args);
	}

}
