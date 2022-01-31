package com.javintx.crm.port.out;

import com.javintx.crm.domain.Customer;

public interface CustomerUpdater {

	/**
	 * Updates an existing {@link Customer}.
	 *
	 * @param customer Customer to uptade.
	 * @return Customer updated.
	 */
	Customer update(final Customer customer);

}
