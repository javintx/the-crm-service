package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;

import java.util.List;

public interface ListAllCustomers {

		/**
			* Get all {@link Customer} in a {@link List}.
			*
			* @return List of customers.
			*/
		List<Customer> get();
}
