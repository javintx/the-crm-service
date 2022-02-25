package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;

public record CustomerResponse(String identifier, String name, String surname,
																															String photo, String userReference) {

		public static CustomerResponse from(final Customer customer) {
				return new CustomerResponse(customer.identifier(), customer.name(), customer.surname(), customer.photo(), customer.userReference());
		}

}
