package com.hangyasi.sporteo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SporteoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SporteoApplication.class, "--debug");
	}

}

