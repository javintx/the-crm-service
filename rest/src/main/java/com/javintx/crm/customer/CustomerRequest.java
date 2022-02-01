package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;

public class CustomerRequest {
		private String id;
		private String name;
		private String surname;
		private String photo;
		private String userId;

		public void setId(String id) {
				this.id = id;
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

		public void setUserId(String userId) {
				this.userId = userId;
		}

		public Customer toDomain() {
				return new Customer(id, name, surname, photo, userId);
		}
}
