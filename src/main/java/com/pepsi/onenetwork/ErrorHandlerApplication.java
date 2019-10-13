package com.pepsi.onenetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ErrorHandlerApplication {
	
	public static void main(String args[])
	{
		SpringApplication.run(ErrorHandlerApplication.class, args);
	}

}
