package com.javintx.crm.usecase;

import com.javintx.crm.domain.Customer;

public interface UpdateCustomer {

	/**
	 * Update customer if exists.
	 *
	 * @param customer {@link Customer} to update. The customer must exist.
	 * @return Customer updated.
	 */
	Customer update(final Customer customer);
}
