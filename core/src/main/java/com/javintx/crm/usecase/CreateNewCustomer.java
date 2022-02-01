package com.javintx.crm.usecase;

import com.javintx.crm.domain.Customer;

public interface CreateNewCustomer {

		/**
			* Create new {@link Customer};
			*
			* @param customer Customer to creates.
			* @return Customer created.
			*/
		Customer with(final Customer customer);
}
