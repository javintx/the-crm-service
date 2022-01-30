package com.javintx.crm.application;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javintx.crm.authentication.Authenticator;
import com.javintx.crm.customer.CustomerRequest;
import com.javintx.crm.customer.CustomerResponse;
import com.javintx.crm.customer.CustomerUseCaseHandler;
import com.javintx.crm.log.ApiRestLogger;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.javintx.crm.customer.CustomerEndPoints.CREATE_NEW_CUSTOMER;
import static com.javintx.crm.customer.CustomerEndPoints.LIST_ALL_CUSTOMERS;
import static org.eclipse.jetty.http.MimeTypes.Type.APPLICATION_JSON;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

public class CustomerController {

	private final CustomerUseCaseHandler customerUseCaseHandler;
	private final Authenticator authenticator;
	private final ApiRestLogger log;

	private final ObjectMapper objectMapper;

	public CustomerController(final int portNumber, final CustomerUseCaseHandler customerUseCaseHandler, final Authenticator authenticator, final ApiRestLogger log) {
		this.customerUseCaseHandler = customerUseCaseHandler;
		this.authenticator = authenticator;
		this.log = log;
		objectMapper = new ObjectMapper();
		objectMapper.configure(FAIL_ON_EMPTY_BEANS, false);
		setupServer(portNumber);
	}

	private void setupServer(final int portNumber) {
		port(portNumber);
		routes();
	}

	private void routes() {
		before(log::apiCall);
		before(authenticator::isAuthenticated);
		get(LIST_ALL_CUSTOMERS.uri, "*/*", this::handleListAllCustomers, objectMapper::writeValueAsString);
		post(CREATE_NEW_CUSTOMER.uri, APPLICATION_JSON.asString(), this::handleCreateNewCustomer, objectMapper::writeValueAsString);
	}

	private List<CustomerResponse> handleListAllCustomers(final Request request, final Response response) {
		return customerUseCaseHandler.get();
	}

	private CustomerResponse handleCreateNewCustomer(final Request request, final Response response) throws IOException {
		try (JsonParser parser = objectMapper.createParser(request.body())) {
			return customerUseCaseHandler.create(parser.readValueAs(CustomerRequest.class));
		}
	}
}
