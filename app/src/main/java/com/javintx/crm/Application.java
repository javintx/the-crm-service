package com.javintx.crm;

import com.javintx.crm.customer.CustomerController;
import com.javintx.crm.customer.CustomerUseCaseHandler;
import com.javintx.crm.port.out.CustomerReader;
import com.javintx.crm.usecase.ListAllCustomers;
import com.javintx.crm.usecase.impl.ListAllCustomersService;

import java.util.Collections;

public class Application {

	private static final int STANDARD_PORT = 8080;

	public static void main(final String[] args) {
		initializeControllers(portFromOrDefault(args));
	}

	private static int portFromOrDefault(final String[] args) {
		var port = STANDARD_PORT;
		if (args.length > 0)
			port = Integer.parseInt(args[0]);
		return port;
	}

	private static void initializeControllers(int port) {
		// As long as the implementation is not done
		final CustomerReader customerReader = Collections::emptyList;

		final ListAllCustomers listAllCustomers = new ListAllCustomersService(customerReader);
		final CustomerUseCaseHandler customerUseCaseHandler = new CustomerUseCaseHandler(listAllCustomers);
		new CustomerController(customerUseCaseHandler, port);
	}

}
