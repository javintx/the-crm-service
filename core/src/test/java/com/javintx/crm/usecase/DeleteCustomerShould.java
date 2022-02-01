package com.javintx.crm.usecase;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.port.out.CustomerDeleter;
import com.javintx.crm.port.out.CustomerReader;
import com.javintx.crm.usecase.exception.CustomerNotExists;
import com.javintx.crm.usecase.impl.DeleteCustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerShould {
	@Mock
	private CustomerReader customerReaderMock;
	@Mock
	private CustomerDeleter customerDeleterMock;

	private DeleteCustomer deleteCustomer;

	@BeforeEach
	void setUp() {
		deleteCustomer = new DeleteCustomerService(customerReaderMock, customerDeleterMock);
	}

	@Test
	void delete_customer_if_customer_exists() {
		Customer existingCustomer = new Customer("id", "name", "surname", "photo");

		when(customerReaderMock.readAll()).thenReturn(List.of(existingCustomer));

		deleteCustomer.delete("id");

		verify(customerDeleterMock).delete("id");
	}

	@Test
	void throw_exception_if_customer_not_exists() {
		when(customerReaderMock.readAll()).thenReturn(Collections.emptyList());

		assertThatThrownBy(() -> deleteCustomer.delete("id")).isExactlyInstanceOf(CustomerNotExists.class);
	}
}
