package com.csvreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.csvreader.config.ConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class SpringCsvDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCsvDataApplication.class, args);
	}

}
