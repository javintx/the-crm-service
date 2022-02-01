package com.javintx.crm.port.out;

import com.javintx.crm.domain.Customer;

public interface CustomerDeleter {

		/**
			* Delete the {@link Customer}.
			*
			* @param customerId Customer ID to delete.
			*/
		void delete(final String customerId);
}
