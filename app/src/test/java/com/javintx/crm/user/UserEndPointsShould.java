package com.javintx.crm.user;

import com.javintx.crm.Application;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.javintx.crm.user.UserEndPoints.CREATE_NEW_USER;
import static com.javintx.crm.user.UserEndPoints.LIST_ALL_USERS;
import static com.javintx.crm.user.UserEndPoints.UPDATE_USER;
import static com.javintx.crm.user.UserEndPointsBindNames.ADMIN_ID;
import static com.javintx.crm.user.UserEndPointsBindNames.USER_ID;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static spark.Spark.awaitInitialization;
import static spark.Spark.awaitStop;
import static spark.Spark.stop;

class UserEndPointsShould {

		private static final int MAX_RANGE_PORT = 60000;
		private static final int MIN_RANGE_PORT = 30000;

		private static final String DELETE_URI = format("/user/delete/{%s}", USER_ID.bindName);

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
				JsonPath jsonPath = given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.get(LIST_ALL_USERS.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.jsonPath();

				assertThat(jsonPath.getString("id")).isEqualTo("[admin]");
				assertThat(jsonPath.getString("name")).isEqualTo("[first admin name]");
				assertThat(jsonPath.getString("surname")).isEqualTo("[first admin surname]");
		}

		@Test
		void return_user_list_with_new_created_user() {
				given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				JsonPath jsonPath = given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.get(LIST_ALL_USERS.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.jsonPath();

				assertThat(jsonPath.getString("id")).isEqualTo("[admin, id]");
				assertThat(jsonPath.getString("name")).isEqualTo("[first admin name, name]");
				assertThat(jsonPath.getString("surname")).isEqualTo("[first admin surname, surname]");

				deleteUser("id");
		}

		@Test
		void return_exception_when_created_existing_user() {
				given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.asString();

				given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_CONFLICT);

				deleteUser("id");
		}

		@Test
		void return_exception_when_created_invalid_user() {
				given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.body("{\"name\":\"name\", \"surname\":\"surname\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_BAD_REQUEST);

				given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.body("{\"id\":\"id\", \"surname\":\"surname\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_BAD_REQUEST);

				given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.body("{\"id\":\"id\", \"name\":\"name\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_BAD_REQUEST);
		}

		@Test
		void return_user_list_with_updated_user() {
				given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.body("{\"id\":\"id\", \"name\":\"name_updated\", \"surname\":\"surname_updated\"}")
						.accept(ContentType.JSON)
						.put(UPDATE_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				JsonPath jsonPath = given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.get(LIST_ALL_USERS.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.jsonPath();

				assertThat(jsonPath.getString("id")).isEqualTo("[admin, id]");
				assertThat(jsonPath.getString("name")).isEqualTo("[first admin name, name_updated]");
				assertThat(jsonPath.getString("surname")).isEqualTo("[first admin surname, surname_updated]");

				deleteUser("id");
		}

		@Test
		void return_exception_when_update_not_existing_user() {
				given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.body("{\"id\":\"id\", \"name\":\"name_updated\", \"surname\":\"surname_updated\"}")
						.accept(ContentType.JSON)
						.put(UPDATE_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_NOT_FOUND);
		}

		@Test
		void return_user_list_without_deleted_user() {
				given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.body("{\"id\":\"id\", \"name\":\"name\", \"surname\":\"surname\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				String deleteResponse = given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.delete(DELETE_URI, "id")
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.asString();
				assertThat(deleteResponse).isEqualTo("\"OK\"");

				JsonPath jsonPath = given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.get(LIST_ALL_USERS.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.jsonPath();

				assertThat(jsonPath.getString("id")).isEqualTo("[admin]");
				assertThat(jsonPath.getString("name")).isEqualTo("[first admin name]");
				assertThat(jsonPath.getString("surname")).isEqualTo("[first admin surname]");
		}

		@Test
		void return_exception_when_user_is_not_admin() {
				given()
						.when()
						.get(LIST_ALL_USERS.uri)
						.then()
						.assertThat()
						.statusCode(SC_FORBIDDEN);
		}

		private void deleteUser(final String userId) {
				given()
						.header(ADMIN_ID.bindName, "admin")
						.when()
						.delete(DELETE_URI, userId)
						.then()
						.assertThat()
						.statusCode(SC_OK);
		}

}
