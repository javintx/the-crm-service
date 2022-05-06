package com.javintx.crm.domain;

import com.javintx.crm.customer.exception.CustomerNotValid;

public record Customer(String identifier, String name, String surname, String photo,
																							String userReference) {

		public Customer(String identifier, String name, String surname, String photo,
																		String userReference) {
				this.identifier = verify(identifier, "identifier");
				this.name = verify(name, "name");
				this.surname = verify(surname, "surname");
				this.photo = photo;
				this.userReference = verify(userReference, "userReference");
		}

		private String verify(final String value, final String field) {
				if (value == null || value.isEmpty() || value.isBlank()) {
						throw new CustomerNotValid(field);
				}
				return value;
		}

}
