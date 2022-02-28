package com.javintx.crm.springboot;

import com.javintx.crm.customer.CustomerDeleterInMemoryAdapter;
import com.javintx.crm.customer.CustomerReaderInMemoryAdapter;
import com.javintx.crm.customer.CustomerUpdaterInMemoryAdapter;
import com.javintx.crm.customer.CustomerUseCaseHandler;
import com.javintx.crm.customer.CustomerWriterInMemoryAdapter;
import com.javintx.crm.customer.impl.CreateNewCustomerService;
import com.javintx.crm.customer.impl.DeleteCustomerService;
import com.javintx.crm.customer.impl.ListAllCustomersService;
import com.javintx.crm.customer.impl.UpdateCustomerService;
import com.javintx.crm.user.UserDeleterInMemoryAdapter;
import com.javintx.crm.user.UserReaderInMemoryAdapter;
import com.javintx.crm.user.UserUpdaterInMemoryAdapter;
import com.javintx.crm.user.UserUseCaseHandler;
import com.javintx.crm.user.UserWriterInMemoryAdapter;
import com.javintx.crm.user.impl.CreateNewUserService;
import com.javintx.crm.user.impl.DeleteUserService;
import com.javintx.crm.user.impl.IsAdminUserService;
import com.javintx.crm.user.impl.ListAllUsersService;
import com.javintx.crm.user.impl.UpdateUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

		@Bean
		public UserUseCaseHandler userUseCaseHandler() {
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

		@Bean
		public static CustomerUseCaseHandler customerUseCaseHandler() {
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
