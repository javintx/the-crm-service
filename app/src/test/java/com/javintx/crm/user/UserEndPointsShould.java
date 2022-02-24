package com.javintx.crm.user;

import com.javintx.crm.Application;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;
import static spark.Spark.awaitInitialization;
import static spark.Spark.awaitStop;
import static spark.Spark.stop;

class UserEndPointsShould {

		private static final int MAX_RANGE_PORT = 60000;
		private static final int MIN_RANGE_PORT = 30000;
		private static final String DELETE_URI = format("/user/delete/{%s}", USER_ID.bindName);
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
		void return_empty_user_list_if_there_are_no_users() {
				var jsonPath = given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
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
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name\", \"surname\":\"surname\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				var jsonPath = given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.get(LIST_ALL_USERS.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.jsonPath();

				assertThat(jsonPath.getString("id")).isEqualTo("[admin, identifier]");
				assertThat(jsonPath.getString("name")).isEqualTo("[first admin name, name]");
				assertThat(jsonPath.getString("surname")).isEqualTo("[first admin surname, surname]");

				deleteUser("id");
		}

		@Test
		void return_exception_when_created_existing_user() {
				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name\", \"surname\":\"surname\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.asString();

				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name\", \"surname\":\"surname\"}")
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
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.body("{\"name\":\"name\", \"surname\":\"surname\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_BAD_REQUEST);

				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.body("{\"identifier\":\"identifier\", \"surname\":\"surname\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_BAD_REQUEST);

				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_BAD_REQUEST);
		}

		@Test
		void return_user_list_with_updated_user() {
				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name\", \"surname\":\"surname\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name_updated\", \"surname\":\"surname_updated\"}")
						.accept(ContentType.JSON)
						.put(UPDATE_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				var jsonPath = given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.get(LIST_ALL_USERS.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.jsonPath();

				assertThat(jsonPath.getString("id")).isEqualTo("[admin, identifier]");
				assertThat(jsonPath.getString("name")).isEqualTo("[first admin name, name_updated]");
				assertThat(jsonPath.getString("surname")).isEqualTo("[first admin surname, surname_updated]");

				deleteUser("id");
		}

		@Test
		void return_exception_when_update_not_existing_user() {
				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name_updated\", \"surname\":\"surname_updated\"}")
						.accept(ContentType.JSON)
						.put(UPDATE_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_NOT_FOUND);
		}

		@Test
		void return_user_list_without_deleted_user() {
				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name\", \"surname\":\"surname\"}")
						.accept(ContentType.JSON)
						.post(CREATE_NEW_USER.uri)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				var deleteResponse = given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.delete(DELETE_URI, "id")
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.asString();
				assertThat(deleteResponse).isEqualTo("\"OK\"");

				var jsonPath = given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
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
						.header(authenticationHeader())
						.when()
						.get(LIST_ALL_USERS.uri)
						.then()
						.assertThat()
						.statusCode(SC_FORBIDDEN);
		}

		@Test
		void return_exception_when_no_authenticated_request() {
				given()
						.when()
						.get(LIST_ALL_USERS.uri)
						.then()
						.assertThat()
						.statusCode(SC_UNAUTHORIZED);
		}

		private void deleteUser(final String userId) {
				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.delete(DELETE_URI, userId)
						.then()
						.assertThat()
						.statusCode(SC_OK);
		}

		private Header authenticationHeader() {
				var claims = new DefaultClaims();
				claims.setSubject("test");
				return new Header("Authorization", "Bearer " + Jwts.builder()
						.setClaims(claims)
						.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
						.signWith(SignatureAlgorithm.HS512, secret)
						.compact());
		}

		private Header adminHeader() {
				return new Header(ADMIN_ID.bindName, "admin");
		}

}
