package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.port.out.customer.CustomerReader;
import com.javintx.crm.customer.impl.ListAllCustomersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ListAllCustomersShould {

		@Mock
		private CustomerReader customerReaderMock;

		private ListAllCustomers listAllCustomers;

		@BeforeEach
		void setUp() {
				listAllCustomers = new ListAllCustomersService(customerReaderMock);
		}

		@Test
		void return_empty_customer_list_if_there_are_no_customers() {
				assertThat(listAllCustomers.get()).isEmpty();
		}

		@Test
		void return_customer_list_if_there_are_customers() {
				Customer customerMock = new Customer("id", "name", "surname");
				when(customerReaderMock.readAll()).thenReturn(List.of(customerMock));
				assertThat(listAllCustomers.get()).isNotEmpty();
		}

}
