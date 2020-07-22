package com.csvreader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringCsvIntegrationTest {
	private static final String PORT_LABEL = "PORT";
	private static final String API_ROOT = "http://localhost:PORT/data";

	@LocalServerPort
	int port;

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void whenGetRecords_thenOK() {
		final Response response = RestAssured.get(API_ROOT.replace(PORT_LABEL, ""+RestAssured.port));
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}
	
	

}
