package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;

public record CustomerDto(String identifier, String name, String surname, String photo, String userReference) {

		public static CustomerDto from(final Customer customer) {
				return new CustomerDto(customer.identifier(), customer.name(), customer.surname(), customer.photo(), customer.userReference());
		}

		public Customer toDomain() {
				return new Customer(identifier, name, surname, photo, userReference);
		}
}
