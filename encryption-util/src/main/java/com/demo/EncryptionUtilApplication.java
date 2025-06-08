package com.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Encryption Utility", version = "1.0", description = "Encryption Utility"))
public class EncryptionUtilApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncryptionUtilApplication.class, args);
	}

}
