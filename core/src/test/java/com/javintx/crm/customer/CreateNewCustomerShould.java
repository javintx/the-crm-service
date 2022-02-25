package com.javintx.crm.customer;

import com.javintx.crm.customer.exception.CustomerAlreadyExists;
import com.javintx.crm.customer.impl.CreateNewCustomerService;
import com.javintx.crm.domain.Customer;
import com.javintx.crm.port.out.customer.CustomerReader;
import com.javintx.crm.port.out.customer.CustomerWriter;
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
				Customer customerToCreate = new Customer("identifier", "name", "surname", "photo", "userReference");
				Customer customerExpected = new Customer("identifier", "name", "surname", "photo", "userReference");

				when(customerWriterMocked.writes(any(Customer.class))).thenReturn(customerExpected);

				Customer customerCreated = createNewCustomer.with(customerToCreate);

				assertThat(customerCreated).isEqualTo(customerExpected);
		}

		@Test
		void throw_exception_when_create_new_customer_that_exists() {
				Customer customerToCreate = new Customer("identifier", "name", "surname", "photo", "userReference");
				Customer customer = new Customer("identifier", "name", "surname", "photo", "userReference");

				when(customerReaderMocked.readAll()).thenReturn(List.of(customer));

				assertThatThrownBy(() -> createNewCustomer.with(customerToCreate)).isExactlyInstanceOf(CustomerAlreadyExists.class);
		}

}
