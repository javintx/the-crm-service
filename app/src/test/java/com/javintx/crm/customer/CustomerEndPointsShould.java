package com.javintx.crm.customer;

import com.javintx.crm.Application;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.javintx.crm.customer.CustomerEndPoints.CREATE_NEW_CUSTOMER;
import static com.javintx.crm.customer.CustomerEndPoints.LIST_ALL_CUSTOMERS;
import static com.javintx.crm.customer.CustomerEndPoints.UPDATE_CUSTOMER;
import static com.javintx.crm.customer.CustomerEndPointsBindNames.CUSTOMER_ID;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;
import static spark.Spark.awaitInitialization;
import static spark.Spark.awaitStop;
import static spark.Spark.stop;

class CustomerEndPointsShould {

		private static final int MAX_RANGE_PORT = 60000;
		private static final int MIN_RANGE_PORT = 30000;
		private static final String DELETE_URI = format("/customer/delete/{%s}", CUSTOMER_ID.bindName);
		private static final long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(10);
		private static String secret;

		private static int port() {
				return new Random().nextInt(MAX_RANGE_PORT - MIN_RANGE_PORT + 1) + MIN_RANGE_PORT;
		}

		public static String secret() {
				return UUID.randomUUID().toString();
		}

		@BeforeAll
		static void startServer() {
				final var port = port();
				secret = secret();
				Application.main(new String[]{String.valueOf(port), secret});
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
						.header(authenticationHeader())
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
						.header(authenticationHeader())
						.when()
						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\", \"photo\":\"photo\", \"userId\":\"userId\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_CUSTOMER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				JsonPath jsonPath = given()
						.header(authenticationHeader())
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
						.header(authenticationHeader())
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
						.header(authenticationHeader())
						.when()
						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\", \"photo\":\"photo\", \"userId\":\"userId\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_CUSTOMER.uri)
						.then()
						.assertThat()
						.statusCode(SC_CONFLICT);

				deleteCustomer("id");
		}

		@Test
		void return_exception_when_created_invalid_customer() {
				given()
						.header(authenticationHeader())
						.when()
						.body("{\"name\":\"name\", \"surname\":\"surname\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_CUSTOMER.uri)
						.then()
						.assertThat()
						.statusCode(SC_BAD_REQUEST);

				given()
						.header(authenticationHeader())
						.when()
						.body("{\"id\":\"id\", \"surname\":\"surname\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_CUSTOMER.uri)
						.then()
						.assertThat()
						.statusCode(SC_BAD_REQUEST);

				given()
						.header(authenticationHeader())
						.when()
						.body("{\"id\":\"id\", \"name\":\"name\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_CUSTOMER.uri)
						.then()
						.assertThat()
						.statusCode(SC_BAD_REQUEST);
		}

		@Test
		void return_customer_list_with_updated_customer() {
				given()
						.header(authenticationHeader())
						.when()
						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\", \"userId\":\"userId\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_CUSTOMER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				given()
						.header(authenticationHeader())
						.when()
						.body("{\"id\":\"id\", \"name\":\"name_updated\", \"surname\":\"surname_updated\", \"photo\":\"photo\", \"userId\":\"userId\"}")
						.accept(ContentType.JSON)
						.put(UPDATE_CUSTOMER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				JsonPath jsonPath = given()
						.header(authenticationHeader())
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
						.header(authenticationHeader())
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
						.header(authenticationHeader())
						.when()
						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\", \"userId\":\"userId\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_CUSTOMER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				String deleteResponse = given()
						.header(authenticationHeader())
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
						.header(authenticationHeader())
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
		void return_exception_when_no_authenticated_request() {
				given()
						.when()
						.get(LIST_ALL_CUSTOMERS.uri)
						.then()
						.assertThat()
						.statusCode(SC_UNAUTHORIZED);
		}

		private void deleteCustomer(final String customerId) {
				given()
						.header(authenticationHeader())
						.when()
						.delete(DELETE_URI, customerId)
						.then()
						.assertThat()
						.statusCode(SC_OK);
		}

		private Header authenticationHeader() {
				DefaultClaims claims = new DefaultClaims();
				claims.setSubject("test");
				return new Header("Authorization", "Bearer " + Jwts.builder()
						.setClaims(claims)
						.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
						.signWith(SignatureAlgorithm.HS512, secret)
						.compact());
		}
}
