package com.javintx.crm.application;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javintx.crm.customer.CustomerRequest;
import com.javintx.crm.customer.CustomerResponse;
import com.javintx.crm.customer.CustomerUseCaseHandler;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.javintx.crm.customer.CustomerEndPoints.CREATE_NEW_CUSTOMER;
import static com.javintx.crm.customer.CustomerEndPoints.LIST_ALL_CUSTOMERS;
import static org.eclipse.jetty.http.MimeTypes.Type.APPLICATION_JSON;
import static spark.Spark.get;
import static spark.Spark.post;

public class CustomerController {

	private final CustomerUseCaseHandler customerUseCaseHandler;
	private final ObjectMapper objectMapper;

	public CustomerController(final CustomerUseCaseHandler customerUseCaseHandler) {
		this.customerUseCaseHandler = customerUseCaseHandler;
		objectMapper = new ObjectMapper();
		objectMapper.configure(FAIL_ON_EMPTY_BEANS, false);
		routes();
	}

	private void routes() {
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
