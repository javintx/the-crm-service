package com.javintx.crm.user;

import com.javintx.crm.application.springboot.SpringBootUserEndPoints;
import com.javintx.crm.springboot.SpringBootApp;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static java.lang.String.format;

class SpringBootUserEndPointsShould extends UserEndPointsShould {

		@BeforeAll
		static void startServer() {
				LIST_ALL_USERS_URI = SpringBootUserEndPoints.LIST_ALL_USERS;
				CREATE_NEW_USER_URI = SpringBootUserEndPoints.CREATE_NEW_USER;
				UPDATE_USER_URI = SpringBootUserEndPoints.UPDATE_USER;
				DELETE_USER_URI = SpringBootUserEndPoints.DELETE_USER;

				final var port = port();
				new SpringBootApp(String.valueOf(port), secret);
				RestAssured.baseURI = format("http://localhost:%s/", port);
		}

		@AfterAll
		static void stopServer() {
//				stop();
//				awaitStop();
		}

}
