package com.javintx.crm.application.springboot;

import com.javintx.crm.Arguments;
import com.javintx.crm.authentication.Authenticator;
import com.javintx.crm.authentication.springboot.JwtSpringBootAuthenticatorAdapter;
import com.javintx.crm.customer.CustomerUseCaseHandler;
import com.javintx.crm.user.UserRequest;
import com.javintx.crm.user.UserUseCaseHandler;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.javintx.crm.application.UseCaseConfigurations.initializeCustomerUseCaseHandler;
import static com.javintx.crm.application.UseCaseConfigurations.initializeUserUseCaseHandler;

@Configuration
public class SpringBootAppConfiguration {

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

		@Bean
		public static CustomerUseCaseHandler customerUseCaseHandler() {
				return initializeCustomerUseCaseHandler();
		}

		@Bean
		public UserUseCaseHandler userUseCaseHandler(final ApplicationArguments arguments) {
				var userUseCaseHandler = initializeUserUseCaseHandler();
				initializeStorage(userUseCaseHandler, Arguments.createAdminOrDefault(arguments.getSourceArgs()));
				return userUseCaseHandler;
		}

		@Bean
		public Authenticator authenticator(final ApplicationArguments arguments) {
				return new JwtSpringBootAuthenticatorAdapter(Arguments.secretFromOrDefault(arguments.getSourceArgs()));
		}
}
