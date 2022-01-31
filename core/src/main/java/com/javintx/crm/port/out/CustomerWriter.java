package com.javintx.crm.port.out;

import com.javintx.crm.domain.Customer;

public interface CustomerWriter {

	/**
	 * Creates a new {@link Customer}.
	 *
	 * @param customer Customer to create.
	 * @return Customer created.
	 */
	Customer writes(final Customer customer);
}
