package com.javintx.crm.user;

import com.javintx.crm.application.springboot.SpringBootUserEndPoints;
import com.javintx.crm.sparkjava.SparkJavaApp;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static java.lang.String.format;
import static spark.Spark.awaitInitialization;
import static spark.Spark.awaitStop;
import static spark.Spark.stop;

class SparkJavaUserEndPointsShould extends UserEndPointsShould {

		@BeforeAll
		static void startServer() {
				// We use the SpringBoot user end points (the path variable to call endpoint from RestAssured has the same format)
				LIST_ALL_USERS_URI = SpringBootUserEndPoints.LIST_ALL_USERS;
				CREATE_NEW_USER_URI = SpringBootUserEndPoints.CREATE_NEW_USER;
				UPDATE_USER_URI = SpringBootUserEndPoints.UPDATE_USER;
				DELETE_USER_URI = SpringBootUserEndPoints.DELETE_USER;

				final var port = port();
				SparkJavaApp.main(new String[]{String.valueOf(port), secret});
				awaitInitialization();
				RestAssured.baseURI = format("http://localhost:%s/", port);
		}

		@AfterAll
		static void stopServer() {
				stop();
				awaitStop();
		}

}
