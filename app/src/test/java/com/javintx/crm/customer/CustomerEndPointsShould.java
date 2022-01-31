package com.javintx.crm.customer;

import com.javintx.crm.Application;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.javintx.crm.customer.CustomerEndPoints.CREATE_NEW_CUSTOMER;
import static com.javintx.crm.customer.CustomerEndPoints.LIST_ALL_CUSTOMERS;
import static com.javintx.crm.customer.CustomerEndPoints.UPDATE_CUSTOMER;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static spark.Spark.awaitInitialization;
import static spark.Spark.awaitStop;
import static spark.Spark.stop;

class CustomerEndPointsShould {

	private static final int MAX_RANGE_PORT = 60000;
	private static final int MIN_RANGE_PORT = 30000;

	// TODO: After every test the persisted values should be removed and then the BeforeEach and AfterEach should be changed to BeforeAll and AfterAll

	private static int port() {
		return new Random().nextInt(MAX_RANGE_PORT - MIN_RANGE_PORT + 1) + MIN_RANGE_PORT;
	}

	@BeforeEach
	void startServer() {
		final var port = port();
		Application.main(new String[]{String.valueOf(port), String.valueOf(false)});
		RestAssured.baseURI = format("http://localhost:%s/", port);
		awaitInitialization();
	}

	@AfterEach
	void stopServer() {
		stop();
		awaitStop();
	}

	@Test
	void return_empty_customer_list_if_there_are_no_customers() {
		String response = given()
			.when()
			.get(LIST_ALL_CUSTOMERS.uri)
			.then()
			.assertThat()
			.statusCode(SC_OK)
			.extract()
			.response()
			.asString();

		assertThat(response).isEqualTo("[]");
	}

	@Test
	void return_customer_list_with_new_created_customer() {
		given()
			.when()
			.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\", \"photo\":\"photo\"}")
			.accept(ContentType.JSON)
			.post(CREATE_NEW_CUSTOMER.uri)
			.then()
			.assertThat()
			.statusCode(SC_OK)
			.extract()
			.response()
			.asString();

		JsonPath jsonPath = given()
			.when()
			.get(LIST_ALL_CUSTOMERS.uri)
			.then()
			.assertThat()
			.statusCode(SC_OK)
			.extract()
			.response()
			.jsonPath();


		assertThat(jsonPath.getString("id")).isEqualTo("[id]");
		assertThat(jsonPath.getString("name")).isEqualTo("[name]");
		assertThat(jsonPath.getString("surname")).isEqualTo("[surname]");
		assertThat(jsonPath.getString("photo")).isEqualTo("[photo]");
	}

	@Test
	void return_customer_list_with_updated_customer() {
		given()
			.when()
			.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\"}")
			.accept(ContentType.JSON)
			.post(CREATE_NEW_CUSTOMER.uri)
			.then()
			.assertThat()
			.statusCode(SC_OK)
			.extract()
			.response()
			.asString();

		given()
			.when()
			.body("{\"id\":\"id\", \"name\":\"name_updated\", \"surname\":\"surname_updated\", \"photo\":\"photo\"}")
			.accept(ContentType.JSON)
			.put(UPDATE_CUSTOMER.uri)
			.then()
			.assertThat()
			.statusCode(SC_OK)
			.extract()
			.response()
			.asString();

		JsonPath jsonPath = given()
			.when()
			.get(LIST_ALL_CUSTOMERS.uri)
			.then()
			.assertThat()
			.statusCode(SC_OK)
			.extract()
			.response()
			.jsonPath();

		assertThat(jsonPath.getString("id")).isEqualTo("[id]");
		assertThat(jsonPath.getString("name")).isEqualTo("[name_updated]");
		assertThat(jsonPath.getString("surname")).isEqualTo("[surname_updated]");
		assertThat(jsonPath.getString("photo")).isEqualTo("[photo]");
	}
}
