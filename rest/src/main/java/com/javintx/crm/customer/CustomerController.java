package com.javintx.crm.customer;

import com.fasterxml.jackson.databind.ObjectMapper;

import static com.javintx.crm.customer.EndPoints.LIST_ALL_CUSTOMERS;
import static spark.Spark.get;
import static spark.Spark.port;

public class CustomerController {
	private final CustomerUseCaseHandler customerUseCaseHandler;
	private final ObjectMapper objectMapper;

	public CustomerController(final CustomerUseCaseHandler customerUseCaseHandler, final int portNumber) {
		this.customerUseCaseHandler = customerUseCaseHandler;
		objectMapper = new ObjectMapper();
		setupServer(portNumber);
	}

	private void setupServer(final int portNumber) {
		port(portNumber);
		routes();
	}

	private void routes() {
		get(LIST_ALL_CUSTOMERS.uri, (request, response) -> customerUseCaseHandler.get(), objectMapper::writeValueAsString);
	}

}
