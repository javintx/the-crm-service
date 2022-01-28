package com.javintx.crm.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.javintx.crm.customer.CustomerEndPoints.CREATE_NEW_CUSTOMER;
import static com.javintx.crm.customer.CustomerEndPoints.LIST_ALL_CUSTOMERS;
import static java.lang.String.format;
import static org.eclipse.jetty.http.MimeTypes.Type.APPLICATION_JSON;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.port;
import static spark.Spark.post;

public class CustomerController {
	private final Logger log = LoggerFactory.getLogger(CustomerController.class);
	private final CustomerUseCaseHandler customerUseCaseHandler;
	private final ObjectMapper objectMapper;

	public CustomerController(final CustomerUseCaseHandler customerUseCaseHandler, final int portNumber) {
		this.customerUseCaseHandler = customerUseCaseHandler;
		objectMapper = new ObjectMapper();
		objectMapper.configure(FAIL_ON_EMPTY_BEANS, false);
		setupServer(portNumber);
	}

	private void setupServer(final int portNumber) {
		port(portNumber);
		routes();
	}

	private void routes() {
		before(this::logApiCall);
		before(this::authenticate);
		get(LIST_ALL_CUSTOMERS.uri, "*/*", this::handleListAllCustomers, objectMapper::writeValueAsString);
		post(CREATE_NEW_CUSTOMER.uri, APPLICATION_JSON.asString(), this::handleCreateNewCustomer, objectMapper::writeValueAsString);
	}

	private void logApiCall(final Request request, final Response response) {
		log.info(format("Received api call: %s", request.uri()));
	}

	private void authenticate(final Request request, final Response response) {
		boolean authenticated = true;
		// TODO: Add authentication method
		if (!authenticated) {
			halt(401, "You are not welcome here");
		}
	}

	private List<CustomerResponse> handleListAllCustomers(final Request request, final Response response) {
		return customerUseCaseHandler.get();
	}

	private CustomerResponse handleCreateNewCustomer(final Request request, final Response response) throws IOException {
		return customerUseCaseHandler.create(objectMapper.createParser(request.body()).readValueAs(CustomerRequest.class));
	}
}
