package com.javintx.crm.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomerInMemoryAdapterShould {
	private CustomerInMemoryAdapter customerInMemoryAdapter;

	@BeforeEach
	public void setUp() {
		customerInMemoryAdapter = new CustomerInMemoryAdapter();
	}

	@Test
	void return_empty_customer_list_if_there_are_no_customers() {
		assertThat(customerInMemoryAdapter.readAll()).isEmpty();
	}

}
