package com.esgi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class AnnualProjectApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnnualProjectApiApplication.class, args);
	}
}
