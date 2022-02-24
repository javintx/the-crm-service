package com.javintx.crm.customer;

import com.javintx.crm.customer.exception.CustomerNotExists;
import com.javintx.crm.customer.impl.UpdateCustomerService;
import com.javintx.crm.domain.Customer;
import com.javintx.crm.port.out.customer.CustomerReader;
import com.javintx.crm.port.out.customer.CustomerUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerShould {

		@Mock
		private CustomerReader customerReaderMock;
		@Mock
		private CustomerUpdater customerUpdaterMock;

		private UpdateCustomer updateCustomer;

		@BeforeEach
		void setUp() {
				updateCustomer = new UpdateCustomerService(customerReaderMock, customerUpdaterMock);
		}

		@Test
		void return_update_customer_if_customer_exists() {
				Customer existingCustomer = new Customer("id1", "name1", "surname1", "photo1", "userId1");
				Customer updatedCustomer = new Customer("id2", "name2", "surname2", "photo2", "userId2");
				Customer customerExpected = new Customer("id2", "name2", "surname2", "photo2", "userId2");

				when(customerReaderMock.readAll()).thenReturn(List.of(existingCustomer));
				when(customerUpdaterMock.update(existingCustomer)).thenReturn(updatedCustomer);

				Customer returnedCustomer = updateCustomer.update(existingCustomer);

				assertThat(returnedCustomer).isEqualTo(customerExpected);
				assertThat(returnedCustomer.identifier()).isEqualTo(customerExpected.identifier());
				assertThat(returnedCustomer.name()).isEqualTo(customerExpected.name());
				assertThat(returnedCustomer.surname()).isEqualTo(customerExpected.surname());
				assertThat(returnedCustomer.photo()).isEqualTo(customerExpected.photo());
				assertThat(returnedCustomer.userReference()).isEqualTo(customerExpected.userReference());
		}

		@Test
		void throw_exception_if_customer_not_exists() {
				Customer customer = new Customer("id", "name", "surname", "photo", "userReference");

				when(customerReaderMock.readAll()).thenReturn(Collections.emptyList());

				assertThatThrownBy(() -> updateCustomer.update(customer)).isExactlyInstanceOf(CustomerNotExists.class);
		}

}
