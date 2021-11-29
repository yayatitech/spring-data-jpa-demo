package com.example.springdatajpademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@SpringBootApplication
public class SpringDataJpaDemoApplication {

	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(SpringDataJpaDemoApplication.class);
		application.setApplicationStartup(new BufferingApplicationStartup(10000));
		application.run(args);
	}

}
