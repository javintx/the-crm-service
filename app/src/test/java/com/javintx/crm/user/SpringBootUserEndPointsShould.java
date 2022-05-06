package com.javintx.crm.user;

import com.javintx.crm.Arguments;
import com.javintx.crm.application.springboot.SpringBootApp;
import com.javintx.crm.application.springboot.SpringBootUserEndPoints;
import io.restassured.RestAssured;
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
				new SpringBootApp("-" + Arguments.PORT.getName(), String.valueOf(port), "-" + Arguments.SECRET.getName(), secret);
				RestAssured.baseURI = format("http://localhost:%s/", port);
		}

}
