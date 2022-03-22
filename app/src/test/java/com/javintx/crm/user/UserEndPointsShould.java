package com.javintx.crm.user;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.javintx.crm.user.UserEndPoints.BindNames.ADMIN_ID;
import static io.restassured.RestAssured.given;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;

abstract class UserEndPointsShould {

		private static final int MAX_RANGE_PORT = 60000;
		private static final int MIN_RANGE_PORT = 30000;
		private static final long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(10);
		protected static String LIST_ALL_USERS_URI;
		protected static String CREATE_NEW_USER_URI;
		protected static String UPDATE_USER_URI;
		protected static String DELETE_USER_URI;
		protected static String secret = secret();

		protected static int port() {
				return new Random().nextInt(MAX_RANGE_PORT - MIN_RANGE_PORT + 1) + MIN_RANGE_PORT;
		}

		protected static String secret() {
				return UUID.randomUUID().toString();
		}

		private static Header authenticationHeader() {
				var claims = new DefaultClaims();
				claims.setSubject("test");
				return new Header("Authorization", "Bearer " + Jwts.builder()
						.setClaims(claims)
						.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
						.signWith(SignatureAlgorithm.HS512, secret)
						.compact());
		}

		private static Header adminHeader() {
				return new Header(ADMIN_ID, "admin");
		}

		@Test
		void return_empty_user_list_if_there_are_no_users() {
				var jsonPath = given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.get(LIST_ALL_USERS_URI)
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.jsonPath();

				assertThat(jsonPath.getString("identifier")).isEqualTo("[admin]");
				assertThat(jsonPath.getString("name")).isEqualTo("[first admin name]");
				assertThat(jsonPath.getString("surname")).isEqualTo("[first admin surname]");
		}

		@Test
		void return_user_list_with_new_created_user() {
				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.contentType(ContentType.JSON)
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name\", \"surname\":\"surname\"}")
						.post(CREATE_NEW_USER_URI)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				var jsonPath = given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.get(LIST_ALL_USERS_URI)
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.jsonPath();

				assertThat(jsonPath.getList("identifier")).containsExactlyInAnyOrder("admin", "identifier");
				assertThat(jsonPath.getList("name")).containsExactlyInAnyOrder("first admin name", "name");
				assertThat(jsonPath.getList("surname")).containsExactlyInAnyOrder("first admin surname", "surname");

				deleteUser("identifier");
		}

		@Test
		void return_exception_when_created_existing_user() {
				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.contentType(ContentType.JSON)
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name\", \"surname\":\"surname\"}")
						.post(CREATE_NEW_USER_URI)
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.asString();

				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.contentType(ContentType.JSON)
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name\", \"surname\":\"surname\"}")
						.post(CREATE_NEW_USER_URI)
						.then()
						.assertThat()
						.statusCode(SC_CONFLICT);

				deleteUser("identifier");
		}

		@Test
		void return_exception_when_created_invalid_user() {
				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.contentType(ContentType.JSON)
						.when()
						.body("{\"name\":\"name\", \"surname\":\"surname\"}")
						.post(CREATE_NEW_USER_URI)
						.then()
						.assertThat()
						.statusCode(SC_BAD_REQUEST);

				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.contentType(ContentType.JSON)
						.when()
						.body("{\"identifier\":\"identifier\", \"surname\":\"surname\"}")
						.post(CREATE_NEW_USER_URI)
						.then()
						.assertThat()
						.statusCode(SC_BAD_REQUEST);

				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.contentType(ContentType.JSON)
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name\"}")
						.post(CREATE_NEW_USER_URI)
						.then()
						.assertThat()
						.statusCode(SC_BAD_REQUEST);
		}

		@Test
		void return_user_list_with_updated_user() {
				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.contentType(ContentType.JSON)
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name\", \"surname\":\"surname\"}")
						.post(CREATE_NEW_USER_URI)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.contentType(ContentType.JSON)
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name_updated\", \"surname\":\"surname_updated\"}")
						.put(UPDATE_USER_URI, "identifier")
						.then()
						.assertThat()
						.statusCode(SC_OK);

				var jsonPath = given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.get(LIST_ALL_USERS_URI)
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.jsonPath();

				assertThat(jsonPath.getList("identifier")).containsExactlyInAnyOrder("admin", "identifier");
				assertThat(jsonPath.getList("name")).containsExactlyInAnyOrder("first admin name", "name_updated");
				assertThat(jsonPath.getList("surname")).containsExactlyInAnyOrder("first admin surname", "surname_updated");

				deleteUser("identifier");
		}

		@Test
		void return_exception_when_update_not_existing_user() {
				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.contentType(ContentType.JSON)
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name_updated\", \"surname\":\"surname_updated\"}")
						.put(UPDATE_USER_URI, "identifier")
						.then()
						.assertThat()
						.statusCode(SC_NOT_FOUND);
		}

		@Test
		void return_user_list_without_deleted_user() {
				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.contentType(ContentType.JSON)
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name\", \"surname\":\"surname\"}")
						.post(CREATE_NEW_USER_URI)
						.then()
						.assertThat()
						.statusCode(SC_OK);

				given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.contentType(ContentType.ANY)
						.when()
						.delete(DELETE_USER_URI, "identifier")
						.then()
						.assertThat()
						.statusCode(SC_OK);

				var jsonPath = given()
						.headers(Headers.headers(authenticationHeader(), adminHeader()))
						.when()
						.get(LIST_ALL_USERS_URI)
						.then()
						.assertThat()
						.statusCode(SC_OK)
						.extract()
						.response()
						.jsonPath();

				assertThat(jsonPath.getString("identifier")).isEqualTo("[admin]");
				assertThat(jsonPath.getString("name")).isEqualTo("[first admin name]");
				assertThat(jsonPath.getString("surname")).isEqualTo("[first admin surname]");
		}

		@Test
		void return_exception_when_user_is_not_admin() {
				given()
						.header(authenticationHeader())
						.when()
						.get(LIST_ALL_USERS_URI)
						.then()
						.assertThat()
						.statusCode(SC_FORBIDDEN);
// NOT WORKS with springboot
				given()
						.headers(Headers.headers(authenticationHeader()))
						.contentType(ContentType.JSON)
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name\", \"surname\":\"surname\"}")
						.post(CREATE_NEW_USER_URI)
						.then()
						.assertThat()
						.statusCode(SC_FORBIDDEN);

				given()
						.headers(Headers.headers(authenticationHeader()))
						.contentType(ContentType.JSON)
						.when()
						.body("{\"identifier\":\"identifier\", \"name\":\"name_updated\", \"surname\":\"surname_updated\"}")
						.put(UPDATE_USER_URI, "identifier")
						.then()
						.assertThat()
						.statusCode(SC_FORBIDDEN);

				given()
						.header(authenticationHeader())
						.when()
						.get(DELETE_USER_URI, "identifier")
						.then()
						.assertThat()
						.statusCode(SC_FORBIDDEN);
		}

		@Test
		void return_exception_when_no_authenticated_request() {
				given()
						.when()
						.get(LIST_ALL_USERS_URI)
						.then()
						.assertThat()
						.statusCode(SC_UNAUTHORIZED);
				// NOT WORKS with springboot
		}

		private void deleteUser(final String userId) {
				given()
						.header(authenticationHeader())
						.header(adminHeader())
						.when()
						.delete(DELETE_USER_URI, userId)
						.then()
						.assertThat()
						.statusCode(SC_OK);
		}

}
