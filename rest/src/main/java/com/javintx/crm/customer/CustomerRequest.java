package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;

public class CustomerRequest {
		private String identifier;
		private String name;
		private String surname;
		private String photo;
		private String userReference;

		public void setIdentifier(String identifier) {
				this.identifier = identifier;
		}

		public void setName(String name) {
				this.name = name;
		}

		public void setSurname(String surname) {
				this.surname = surname;
		}

		public void setPhoto(String photo) {
				this.photo = photo;
		}

		public void setUserReference(String userReference) {
				this.userReference = userReference;
		}

		public Customer toDomain() {
				return new Customer(identifier, name, surname, photo, userReference);
		}
}
