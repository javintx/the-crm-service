package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;

public record CustomerRequest(String identifier, String name, String surname,
																														String photo, String userReference) {

		public Customer toDomain() {
				return new Customer(identifier, name, surname, photo, userReference);
		}

}
