package com.javintx.crm.customer;

import com.javintx.crm.Arguments;
import com.javintx.crm.application.springboot.SpringBootApp;
import com.javintx.crm.application.springboot.SpringBootCustomerEndPoints;
import io.restassured.RestAssured;
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
				new SpringBootApp("-" + Arguments.PORT.getName(), String.valueOf(port), "-" + Arguments.SECRET.getName(), secret);
				RestAssured.baseURI = format("http://localhost:%s/", port);
		}

}
