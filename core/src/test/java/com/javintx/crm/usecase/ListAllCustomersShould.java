package com.javintx.crm.usecase;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ListAllCustomersShould {

	@Test
	void return_empty_customer_list_if_there_are_no_customers() {
		ListAllCustomers useCase = new ListAllCustomers();
		assertThat(useCase.get()).isEmpty();
	}
}
