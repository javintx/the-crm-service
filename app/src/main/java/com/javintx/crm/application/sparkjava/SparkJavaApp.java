package com.javintx.crm.application.sparkjava;

import com.javintx.crm.Arguments;
import com.javintx.crm.authentication.sparkjava.JwtSparkAuthenticatorAdapter;
import com.javintx.crm.log.Slf4JApiRestLoggerAdapter;
import com.javintx.crm.user.UserRequest;
import com.javintx.crm.user.UserUseCaseHandler;

import static com.javintx.crm.application.UseCaseConfigurations.initializeCustomerUseCaseHandler;
import static com.javintx.crm.application.UseCaseConfigurations.initializeUserUseCaseHandler;

public class SparkJavaApp {

		public SparkJavaApp(final String... args) {
				initializeControllers(Arguments.portFromOrDefault(args), Arguments.secretFromOrDefault(args), Arguments.createAdminOrDefault(args));
		}

		private void initializeControllers(final int port, final String secret, final boolean createAdmin) {
				final var userUseCaseHandler = initializeUserUseCaseHandler();
				final var customerUseCaseHandler = initializeCustomerUseCaseHandler();

				initializeStorage(userUseCaseHandler, createAdmin);

				final var authenticator = new JwtSparkAuthenticatorAdapter(secret);
				final var applicationLog = new Slf4JApiRestLoggerAdapter(ApplicationController.class);

				new ApplicationController(port, authenticator, applicationLog);
				new CustomerController(customerUseCaseHandler);
				new UserController(userUseCaseHandler);
		}

		private void initializeStorage(final UserUseCaseHandler userUseCaseHandler, final boolean createAdmin) {
				if (createAdmin) {
						ensureEmptyDatabase(userUseCaseHandler);
						var adminUserRequest = new UserRequest("admin", "first admin name", "first admin surname", true);
						userUseCaseHandler.create(adminUserRequest);
				}
		}

		private void ensureEmptyDatabase(final UserUseCaseHandler userUseCaseHandler) {
				// Ensure that there are no users before
				if (!userUseCaseHandler.get().isEmpty()) {
						userUseCaseHandler.delete("admin");
				}
		}

}
