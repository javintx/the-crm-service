package com.javintx.crm.port.out;

import com.javintx.crm.domain.Customer;

import java.util.List;

public interface CustomerReader {

	/**
	 * Get all {@link Customer} in a {@link List}.
	 *
	 * @return List of customers.
	 */
	List<Customer> getAllCustomers();
}
