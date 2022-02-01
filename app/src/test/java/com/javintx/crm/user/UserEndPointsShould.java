package com.javintx.crm.user;

import com.javintx.crm.Application;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.javintx.crm.user.UserEndPoints.LIST_ALL_USERS;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static spark.Spark.awaitInitialization;
import static spark.Spark.awaitStop;
import static spark.Spark.stop;

class UserEndPointsShould {

		private static final int MAX_RANGE_PORT = 60000;
		private static final int MIN_RANGE_PORT = 30000;
//		private static final String DELETE_URI = format("/user/delete/{%s}", USER_ID.bindName);

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
		void return_empty_user_list_if_there_are_no_users() {
				String response = given()
						.when()
						.get(LIST_ALL_USERS.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.asString();

				assertThat(response).isEqualTo("[]");
		}

//		@Test
//		void return_user_list_with_new_created_user() {
//				given()
//						.when()
//						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\", \"photo\":\"photo\"}")
//						.accept(ContentType.JSON)
//						.post(CREATE_NEW_USER.uri)
//						.then()
//						.assertThat()
//						.statusCode(SC_OK);
//
//				JsonPath jsonPath = given()
//						.when()
//						.get(LIST_ALL_USERS.uri)
//						.then()
//						.assertThat()
//						.statusCode(SC_OK)
//						.extract()
//						.response()
//						.jsonPath();
//
//
//				assertThat(jsonPath.getString("id")).isEqualTo("[id]");
//				assertThat(jsonPath.getString("name")).isEqualTo("[name]");
//				assertThat(jsonPath.getString("surname")).isEqualTo("[surname]");
//				assertThat(jsonPath.getString("photo")).isEqualTo("[photo]");
//
//				deleteUser("id");
//		}
//
//		@Test
//		void return_exception_when_created_existing_user() {
//				given()
//						.when()
//						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\", \"photo\":\"photo\"}")
//						.accept(ContentType.JSON)
//						.post(CREATE_NEW_USER.uri)
//						.then()
//						.assertThat()
//						.statusCode(SC_OK)
//						.extract()
//						.response()
//						.asString();
//
//				given()
//						.when()
//						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\", \"photo\":\"photo\"}")
//						.accept(ContentType.JSON)
//						.put(CREATE_NEW_USER.uri)
//						.then()
//						.assertThat()
//						.statusCode(SC_NOT_FOUND);
//
//				deleteUser("id");
//		}
//
//		@Test
//		void return_user_list_with_updated_user() {
//				given()
//						.when()
//						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\"}")
//						.accept(ContentType.JSON)
//						.post(CREATE_NEW_USER.uri)
//						.then()
//						.assertThat()
//						.statusCode(SC_OK);
//
//				given()
//						.when()
//						.body("{\"id\":\"id\", \"name\":\"name_updated\", \"surname\":\"surname_updated\", \"photo\":\"photo\"}")
//						.accept(ContentType.JSON)
//						.put(UPDATE_USER.uri)
//						.then()
//						.assertThat()
//						.statusCode(SC_OK);
//
//				JsonPath jsonPath = given()
//						.when()
//						.get(LIST_ALL_USERS.uri)
//						.then()
//						.assertThat()
//						.statusCode(SC_OK)
//						.extract()
//						.response()
//						.jsonPath();
//
//				assertThat(jsonPath.getString("id")).isEqualTo("[id]");
//				assertThat(jsonPath.getString("name")).isEqualTo("[name_updated]");
//				assertThat(jsonPath.getString("surname")).isEqualTo("[surname_updated]");
//				assertThat(jsonPath.getString("photo")).isEqualTo("[photo]");
//
//				deleteUser("id");
//		}
//
//		@Test
//		void return_exception_when_update_not_existing_user() {
//				given()
//						.when()
//						.body("{\"id\":\"id\", \"name\":\"name_updated\", \"surname\":\"surname_updated\", \"photo\":\"photo\"}")
//						.accept(ContentType.JSON)
//						.put(UPDATE_USER.uri)
//						.then()
//						.assertThat()
//						.statusCode(SC_NOT_FOUND);
//		}
//
//		@Test
//		void return_user_list_without_deleted_user() {
//				given()
//						.when()
//						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\"}")
//						.accept(ContentType.JSON)
//						.post(CREATE_NEW_USER.uri)
//						.then()
//						.assertThat()
//						.statusCode(SC_OK);
//
//				String deleteResponse = given()
//						.when()
//						.delete(DELETE_URI, "id")
//						.then()
//						.assertThat()
//						.statusCode(SC_OK)
//						.extract()
//						.response()
//						.asString();
//				assertThat(deleteResponse).isEqualTo("\"OK\"");
//
//				String response = given()
//						.when()
//						.get(LIST_ALL_USERS.uri)
//						.then()
//						.assertThat()
//						.statusCode(SC_OK)
//						.extract()
//						.response()
//						.asString();
//
//				assertThat(response).isEqualTo("[]");
//		}
//
//		private void deleteUser(final String userId) {
//				given()
//						.when()
//						.delete(DELETE_URI, userId)
//						.then()
//						.assertThat()
//						.statusCode(SC_OK);
//		}


}
