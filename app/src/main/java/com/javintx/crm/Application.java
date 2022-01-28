package com.javintx.crm;

import com.javintx.crm.customer.CustomerController;
import com.javintx.crm.customer.CustomerInMemoryAdapter;
import com.javintx.crm.customer.CustomerUseCaseHandler;
import com.javintx.crm.usecase.CreateNewCustomer;
import com.javintx.crm.usecase.ListAllCustomers;
import com.javintx.crm.usecase.impl.CreateNewCustomerService;
import com.javintx.crm.usecase.impl.ListAllCustomersService;

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
		final CustomerInMemoryAdapter customerInMemoryAdapter = new CustomerInMemoryAdapter();

		final ListAllCustomers listAllCustomers = new ListAllCustomersService(customerInMemoryAdapter);
		final CreateNewCustomer createNewCustomer = new CreateNewCustomerService(customerInMemoryAdapter);
		final CustomerUseCaseHandler customerUseCaseHandler = new CustomerUseCaseHandler(listAllCustomers, createNewCustomer);

		new CustomerController(customerUseCaseHandler, port);
	}

}
