package com.javintx.crm.domain;

import com.javintx.crm.customer.exception.CustomerNotValid;

public record Customer(String identifier, String name, String surname, String photo,
																							String userReference) {

		public Customer(String identifier, String name, String surname, String photo,
																		String userReference) {
				this.identifier = identifier;
				this.name = name;
				this.surname = surname;
				this.photo = photo;
				this.userReference = userReference;
				verify();
		}

		private void verify() {
				if (identifier == null || name == null || surname == null || userReference == null) {
						throw new CustomerNotValid(this);
				}
		}

}
