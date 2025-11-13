package com.bahanajar.bahanajar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "API To Do List", 
		version = "1.0.0", 
		description = "Dokumentasi API untuk Aplikasi To Do List Belajar Spring Boot"
	)
)
public class BahanajarApplication {

	public static void main(String[] args) {
		SpringApplication.run(BahanajarApplication.class, args);
	}

}
