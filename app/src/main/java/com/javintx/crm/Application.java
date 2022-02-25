package com.javintx.crm;

import com.javintx.crm.application.ApplicationController;
import com.javintx.crm.application.CustomerController;
import com.javintx.crm.application.UserController;
import com.javintx.crm.authentication.JwtAuthenticatorAdapter;
import com.javintx.crm.customer.CustomerDeleterInMemoryAdapter;
import com.javintx.crm.customer.CustomerReaderInMemoryAdapter;
import com.javintx.crm.customer.CustomerUpdaterInMemoryAdapter;
import com.javintx.crm.customer.CustomerUseCaseHandler;
import com.javintx.crm.customer.CustomerWriterInMemoryAdapter;
import com.javintx.crm.customer.impl.CreateNewCustomerService;
import com.javintx.crm.customer.impl.DeleteCustomerService;
import com.javintx.crm.customer.impl.ListAllCustomersService;
import com.javintx.crm.customer.impl.UpdateCustomerService;
import com.javintx.crm.log.Slf4JApiRestLoggerAdapter;
import com.javintx.crm.user.UserDeleterInMemoryAdapter;
import com.javintx.crm.user.UserReaderInMemoryAdapter;
import com.javintx.crm.user.UserRequest;
import com.javintx.crm.user.UserUpdaterInMemoryAdapter;
import com.javintx.crm.user.UserUseCaseHandler;
import com.javintx.crm.user.UserWriterInMemoryAdapter;
import com.javintx.crm.user.impl.CreateNewUserService;
import com.javintx.crm.user.impl.DeleteUserService;
import com.javintx.crm.user.impl.IsAdminUserService;
import com.javintx.crm.user.impl.ListAllUsersService;
import com.javintx.crm.user.impl.UpdateUserService;

public class Application {

		private static final int STANDARD_PORT = 8080;
		private static final String SECRET = "changeIt";
		private static final boolean CREATE_ADMIN = true;

		public static void main(final String[] args) {
				initializeControllers(portFromOrDefault(args), secretFromOrDefault(args), createAdminOrDefault(args));
		}

		private static int portFromOrDefault(final String[] args) {
				var port = STANDARD_PORT;
				if (args.length > 0)
						port = Integer.parseInt(args[0]);
				return port;
		}

		private static String secretFromOrDefault(final String[] args) {
				var secret = SECRET;
				if (args.length > 1)
						secret = args[1];
				return secret;
		}

		private static boolean createAdminOrDefault(final String[] args) {
				var createAdmin = CREATE_ADMIN;
				if (args.length > 2)
						createAdmin = Boolean.parseBoolean(args[2]);
				return createAdmin;
		}

		private static void initializeControllers(final int port, final String secret, final boolean createAdmin) {
				final var userUseCaseHandler = initializeUserUseCaseHandler();
				final var customerUseCaseHandler = initializeCustomerUseCaseHandler();

				initializeStorage(userUseCaseHandler, createAdmin);

				final var authenticator = new JwtAuthenticatorAdapter(secret);
				final var applicationLog = new Slf4JApiRestLoggerAdapter(ApplicationController.class);

				new ApplicationController(port, authenticator, applicationLog);
				new CustomerController(customerUseCaseHandler);
				new UserController(userUseCaseHandler);
		}

		private static void initializeStorage(final UserUseCaseHandler userUseCaseHandler, final boolean createAdmin) {
				if (createAdmin) {
						ensureEmptyDatabase(userUseCaseHandler);
						var adminUserRequest = new UserRequest("admin", "first admin name", "first admin surname", true);
						userUseCaseHandler.create(adminUserRequest);
				}
		}

		private static void ensureEmptyDatabase(final UserUseCaseHandler userUseCaseHandler) {
				// Ensure that there are no users before
				if (!userUseCaseHandler.get().isEmpty()) {
						userUseCaseHandler.delete("admin");
				}
		}

		private static UserUseCaseHandler initializeUserUseCaseHandler() {
				final var userReader = new UserReaderInMemoryAdapter();
				final var userWriter = new UserWriterInMemoryAdapter();
				final var userUpdater = new UserUpdaterInMemoryAdapter();
				final var userDeleter = new UserDeleterInMemoryAdapter();

				final var listAllUsers = new ListAllUsersService(userReader);
				final var createNewUser = new CreateNewUserService(userWriter, userReader);
				final var updateUser = new UpdateUserService(userReader, userUpdater);
				final var deleteUser = new DeleteUserService(userReader, userDeleter);
				final var isAdminUser = new IsAdminUserService(userReader);

				return new UserUseCaseHandler(listAllUsers, createNewUser, updateUser, deleteUser, isAdminUser);
		}

		private static CustomerUseCaseHandler initializeCustomerUseCaseHandler() {
				final var customerReader = new CustomerReaderInMemoryAdapter();
				final var customerWriter = new CustomerWriterInMemoryAdapter();
				final var customerUpdater = new CustomerUpdaterInMemoryAdapter();
				final var customerDeleter = new CustomerDeleterInMemoryAdapter();

				final var listAllCustomers = new ListAllCustomersService(customerReader);
				final var createNewCustomer = new CreateNewCustomerService(customerWriter, customerReader);
				final var updateCustomer = new UpdateCustomerService(customerReader, customerUpdater);
				final var deleteCustomer = new DeleteCustomerService(customerReader, customerDeleter);

				return new CustomerUseCaseHandler(listAllCustomers, createNewCustomer, updateCustomer, deleteCustomer);
		}

}
