package com.javintx.crm.usecase;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.port.out.CustomerReader;
import com.javintx.crm.port.out.CustomerWriter;
import com.javintx.crm.usecase.exception.CustomerAlreadyExists;
import com.javintx.crm.usecase.impl.CreateNewCustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateNewCustomerShould {

		@Mock
		private CustomerWriter customerWriterMocked;
		@Mock
		private CustomerReader customerReaderMocked;

		private CreateNewCustomer createNewCustomer;

		@BeforeEach
		void setUp() {
				createNewCustomer = new CreateNewCustomerService(customerWriterMocked, customerReaderMocked);
		}

		@Test
		void create_new_customer() {
				Customer customerToCreate = new Customer("id", "name", "surname");
				Customer customerExpected = new Customer("id", "name", "surname");

				when(customerWriterMocked.writes(any(Customer.class))).thenReturn(customerExpected);

				Customer customerCreated = createNewCustomer.with(customerToCreate);

				assertThat(customerCreated).isEqualTo(customerExpected);
		}

		@Test
		void throw_exception_when_create_new_customer_that_exists() {
				Customer customerToCreate = new Customer("id", "name", "surname");
				Customer customer = new Customer("id", "name", "surname");

				when(customerReaderMocked.readAll()).thenReturn(List.of(customer));

				assertThatThrownBy(() -> createNewCustomer.with(customerToCreate)).isExactlyInstanceOf(CustomerAlreadyExists.class);
		}

}
