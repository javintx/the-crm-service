package com.javintx.crm.customer;

import com.javintx.crm.Application;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.javintx.crm.customer.CustomerEndPoints.LIST_ALL_CUSTOMERS;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static spark.Spark.awaitInitialization;
import static spark.Spark.awaitStop;
import static spark.Spark.stop;

class CustomerEndPointsShould {

	private static final int MAX_RANGE_PORT = 60000;
	private static final int MIN_RANGE_PORT = 30000;

	@BeforeAll
	static void startServer() {
		final var port = port();
		Application.main(new String[]{String.valueOf(port), String.valueOf(false)});
		RestAssured.baseURI = format("http://localhost:%s/", port);
		awaitInitialization();
	}

	@AfterAll
	static void stopServer() {
		stop();
		awaitStop();
	}

	private static int port() {
		return new Random().nextInt(MAX_RANGE_PORT - MIN_RANGE_PORT + 1) + MIN_RANGE_PORT;
	}

	@Test
	void return_empty_customer_list_if_there_are_no_customers() {
		given()
			.when()
			.get(LIST_ALL_CUSTOMERS.uri)
			.then()
			.assertThat()
			.statusCode(SC_OK)
			.extract()
			.response()
			.asString();
	}


}
