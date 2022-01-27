package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.usecase.ListAllCustomers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerUseCaseHandlerShould {

	@Mock
	private ListAllCustomers listAllCustomersMock;

	private CustomerUseCaseHandler customerUseCaseHandler;

	@BeforeEach
	public void setUp() {
		customerUseCaseHandler = new CustomerUseCaseHandler(listAllCustomersMock);
	}

	@Test
	void return_empty_customer_list_if_there_are_no_customers() {
		assertThat(customerUseCaseHandler.get()).isEmpty();
	}

	@Test
	void return_customer_list_if_there_are_customers() {
		Customer customerMock = new Customer();
		when(listAllCustomersMock.get()).thenReturn(List.of(customerMock));
		assertThat(customerUseCaseHandler.get()).isNotEmpty();
	}
}
