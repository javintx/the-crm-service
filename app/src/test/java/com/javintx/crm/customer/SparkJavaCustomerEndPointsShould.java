package com.javintx.crm.customer;

import com.javintx.crm.application.springboot.SpringBootCustomerEndPoints;
import com.javintx.crm.sparkjava.SparkJavaApp;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static java.lang.String.format;
import static spark.Spark.awaitInitialization;
import static spark.Spark.awaitStop;
import static spark.Spark.stop;

class SparkJavaCustomerEndPointsShould extends CustomerEndPointsShould {

		@BeforeAll
		static void startServer() {
				// We use the SpringBoot user end points (the path variable to call endpoint from RestAssured has the same format)
				LIST_ALL_CUSTOMERS_URI = SpringBootCustomerEndPoints.LIST_ALL_CUSTOMERS;
				CREATE_NEW_CUSTOMER_URI = SpringBootCustomerEndPoints.CREATE_NEW_CUSTOMER;
				UPDATE_CUSTOMER_URI = SpringBootCustomerEndPoints.UPDATE_CUSTOMER;
				DELETE_CUSTOMER_URI = SpringBootCustomerEndPoints.DELETE_CUSTOMER;

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
