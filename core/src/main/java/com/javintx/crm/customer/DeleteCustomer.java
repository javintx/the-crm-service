package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;

public interface DeleteCustomer {

		/**
			* Delete the {@link Customer}.
			*
			* @param customerId Customer ID to delete.
			*/
		void delete(final String customerId);
}
