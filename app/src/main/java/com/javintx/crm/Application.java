package com.javintx.crm;

import com.javintx.crm.application.ApplicationController;
import com.javintx.crm.application.CustomerController;
import com.javintx.crm.authentication.AlwaysTrueAuthenticatorAdapter;
import com.javintx.crm.authentication.Authenticator;
import com.javintx.crm.customer.CustomerInMemoryAdapter;
import com.javintx.crm.customer.CustomerUseCaseHandler;
import com.javintx.crm.log.ApiRestLogger;
import com.javintx.crm.log.Slf4JApiRestLoggerAdapter;
import com.javintx.crm.usecase.CreateNewCustomer;
import com.javintx.crm.usecase.DeleteCustomer;
import com.javintx.crm.usecase.ListAllCustomers;
import com.javintx.crm.usecase.UpdateCustomer;
import com.javintx.crm.usecase.impl.CreateNewCustomerService;
import com.javintx.crm.usecase.impl.DeleteCustomerService;
import com.javintx.crm.usecase.impl.ListAllCustomersService;
import com.javintx.crm.usecase.impl.UpdateCustomerService;

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
				final CreateNewCustomer createNewCustomer = new CreateNewCustomerService(customerInMemoryAdapter, customerInMemoryAdapter);
				final UpdateCustomer updateCustomer = new UpdateCustomerService(customerInMemoryAdapter, customerInMemoryAdapter);
				final DeleteCustomer deleteCustomer = new DeleteCustomerService(customerInMemoryAdapter, customerInMemoryAdapter);
				final CustomerUseCaseHandler customerUseCaseHandler = new CustomerUseCaseHandler(listAllCustomers, createNewCustomer, updateCustomer, deleteCustomer);

				final Authenticator authenticator = new AlwaysTrueAuthenticatorAdapter();
				final ApiRestLogger applicationLog = new Slf4JApiRestLoggerAdapter(ApplicationController.class);

				new ApplicationController(port, authenticator, applicationLog);
				new CustomerController(customerUseCaseHandler);
		}

}
