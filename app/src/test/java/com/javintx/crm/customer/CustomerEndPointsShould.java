package com.javintx.crm.customer;

import com.javintx.crm.Application;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.javintx.crm.customer.CustomerEndPoints.CREATE_NEW_CUSTOMER;
import static com.javintx.crm.customer.CustomerEndPoints.LIST_ALL_CUSTOMERS;
import static com.javintx.crm.customer.CustomerEndPoints.UPDATE_CUSTOMER;
import static com.javintx.crm.customer.CustomerEndPointsBindNames.CUSTOMER_ID;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static spark.Spark.awaitInitialization;
import static spark.Spark.awaitStop;
import static spark.Spark.stop;

class CustomerEndPointsShould {

		private static final int MAX_RANGE_PORT = 60000;
		private static final int MIN_RANGE_PORT = 30000;
		private static final String DELETE_URI = format("/customer/delete/{%s}", CUSTOMER_ID.bindName);

		private static int port() {
				return new Random().nextInt(MAX_RANGE_PORT - MIN_RANGE_PORT + 1) + MIN_RANGE_PORT;
		}

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
						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\", \"photo\":\"photo\", \"userId\":\"userId\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_CUSTOMER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

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
				assertThat(jsonPath.getString("userId")).isEqualTo("[userId]");

				deleteCustomer("id");
		}

		@Test
		void return_exception_when_created_existing_customer() {
				given()
						.when()
						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\", \"photo\":\"photo\", \"userId\":\"userId\"}")
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
						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\", \"photo\":\"photo\", \"userId\":\"userId\"}")
						.accept(ContentType.JSON)
						.put(CREATE_NEW_CUSTOMER.uri)
						.then()
						.assertThat()
						.statusCode(SC_NOT_FOUND);

				deleteCustomer("id");
		}

		@Test
		void return_customer_list_with_updated_customer() {
				given()
						.when()
						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\", \"userId\":\"userId\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_CUSTOMER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				given()
						.when()
						.body("{\"id\":\"id\", \"name\":\"name_updated\", \"surname\":\"surname_updated\", \"photo\":\"photo\", \"userId\":\"userId\"}")
						.accept(ContentType.JSON)
						.put(UPDATE_CUSTOMER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

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
				assertThat(jsonPath.getString("userId")).isEqualTo("[userId]");

				deleteCustomer("id");
		}

		@Test
		void return_exception_when_update_not_existing_customer() {
				given()
						.when()
						.body("{\"id\":\"id\", \"name\":\"name_updated\", \"surname\":\"surname_updated\", \"photo\":\"photo\", \"userId\":\"userId\"}")
						.accept(ContentType.JSON)
						.put(UPDATE_CUSTOMER.uri)
						.then()
						.assertThat()
						.statusCode(SC_NOT_FOUND);
		}

		@Test
		void return_customer_list_without_deleted_customer() {
				given()
						.when()
						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\", \"userId\":\"userId\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_CUSTOMER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				String deleteResponse = given()
						.when()
						.delete(DELETE_URI, "id")
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.asString();
				assertThat(deleteResponse).isEqualTo("\"OK\"");

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

		private void deleteCustomer(final String customerId) {
				given()
						.when()
						.delete(DELETE_URI, customerId)
						.then()
						.assertThat()
						.statusCode(SC_OK);
		}


}
