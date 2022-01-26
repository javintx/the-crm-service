package com.javintx.crm.usecase;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.port.out.CustomerReader;
import com.javintx.crm.usecase.impl.ListAllCustomersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListAllCustomersShould {

	@Mock
	private CustomerReader customerReaderMock;

	private ListAllCustomers useCase;

	@BeforeEach
	void setUp() {
		useCase = new ListAllCustomersService(customerReaderMock);
	}

	@Test
	void return_empty_customer_list_if_there_are_no_customers() {
		assertThat(useCase.get()).isEmpty();
	}

	@Test
	void return_customer_list_if_there_are_customers() {
		Customer customerMock = mock(Customer.class);
		when(customerReaderMock.getAllCustomers()).thenReturn(List.of(customerMock));
		assertThat(useCase.get()).isNotEmpty();
	}

}