package com.csvreader.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "csv")
@Getter
@Setter
public class ConfigProperties {

	String filepath;
	int recordsPerPage;
	String appName;
}
