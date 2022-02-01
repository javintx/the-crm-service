package com.javintx.crm.application;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javintx.crm.customer.CustomerRequest;
import com.javintx.crm.customer.CustomerResponse;
import com.javintx.crm.customer.CustomerUseCaseHandler;
import com.javintx.crm.usecase.exception.CustomerAlreadyExists;
import com.javintx.crm.usecase.exception.CustomerNotExists;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.javintx.crm.customer.CustomerEndPoints.CREATE_NEW_CUSTOMER;
import static com.javintx.crm.customer.CustomerEndPoints.DELETE_CUSTOMER;
import static com.javintx.crm.customer.CustomerEndPoints.LIST_ALL_CUSTOMERS;
import static com.javintx.crm.customer.CustomerEndPoints.UPDATE_CUSTOMER;
import static com.javintx.crm.customer.CustomerEndPointsBindNames.CUSTOMER_ID;
import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.eclipse.jetty.http.MimeTypes.Type.APPLICATION_JSON;
import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

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
		put(UPDATE_CUSTOMER.uri, APPLICATION_JSON.asString(), this::handleUpdateCustomer, objectMapper::writeValueAsString);
		delete(DELETE_CUSTOMER.uri, "*/*", this::handleDeleteCustomer, objectMapper::writeValueAsString);
		exceptions();
	}

	private void exceptions() {
		exception(CustomerNotExists.class, (e, request, response) -> {
			response.status(SC_NOT_FOUND);
			response.body(e.getMessage());
		});
		exception(CustomerAlreadyExists.class, (e, request, response) -> {
			response.status(SC_CONFLICT);
			response.body(e.getMessage());
		});
	}

	private List<CustomerResponse> handleListAllCustomers(final Request request, final Response response) {
		return customerUseCaseHandler.get();
	}

	private CustomerResponse handleCreateNewCustomer(final Request request, final Response response) throws IOException {
		try (JsonParser parser = objectMapper.createParser(request.body())) {
			return customerUseCaseHandler.create(parser.readValueAs(CustomerRequest.class));
		}
	}

	private CustomerResponse handleUpdateCustomer(final Request request, final Response response) throws IOException {
		try (JsonParser parser = objectMapper.createParser(request.body())) {
			return customerUseCaseHandler.update(parser.readValueAs(CustomerRequest.class));
		}
	}

	private String handleDeleteCustomer(final Request request, final Response response) {
		customerUseCaseHandler.delete(request.params(CUSTOMER_ID.bindName));
		response.status(SC_OK);
		return "OK";
	}
}
