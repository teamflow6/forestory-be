package com.teamflow.forestory_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ForestoryBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForestoryBeApplication.class, args);
	}

}
