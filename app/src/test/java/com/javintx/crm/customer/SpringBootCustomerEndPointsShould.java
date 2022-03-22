package com.javintx.crm.customer;

import com.javintx.crm.application.springboot.SpringBootCustomerEndPoints;
import com.javintx.crm.springboot.SpringBootApp;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static java.lang.String.format;

class SpringBootCustomerEndPointsShould extends CustomerEndPointsShould {

		@BeforeAll
		static void startServer() {
				LIST_ALL_CUSTOMERS_URI = SpringBootCustomerEndPoints.LIST_ALL_CUSTOMERS;
				CREATE_NEW_CUSTOMER_URI = SpringBootCustomerEndPoints.CREATE_NEW_CUSTOMER;
				UPDATE_CUSTOMER_URI = SpringBootCustomerEndPoints.UPDATE_CUSTOMER;
				DELETE_CUSTOMER_URI = SpringBootCustomerEndPoints.DELETE_CUSTOMER;

				final var port = port();
				SpringBootApp.main(new String[]{String.valueOf(port), secret});
				RestAssured.baseURI = format("http://localhost:%s/", port);
		}

		@AfterAll
		static void stopServer() {
//				stop();
//				awaitStop();
		}

}
