package com.javintx.crm.domain;

import com.javintx.crm.customer.exception.CustomerNotValid;

import java.util.Objects;

public final class Customer {

		private final String id;
		private final String name;
		private final String surname;
		private final String photo;
		private final String userId;

		public Customer(String id, String name, String surname, String photo, String userId) {
				this.id = id;
				this.name = name;
				this.surname = surname;
				this.photo = photo;
				this.userId = userId;
				verify();
		}

		private void verify() {
				if (id == null || name == null || surname == null || userId == null) {
						throw new CustomerNotValid(this);
				}
		}

		public String identifier() {
				return id;
		}

		public String name() {
				return name;
		}

		public String surname() {
				return surname;
		}

		public String photo() {
				return photo;
		}

		public String userReference() {
				return userId;
		}

		@Override
		public boolean equals(Object o) {
				if (this == o) return true;
				if (o == null || getClass() != o.getClass()) return false;
				Customer customer = (Customer) o;
				return id.equals(customer.id) && name.equals(customer.name) && surname.equals(customer.surname) && Objects.equals(photo, customer.photo) && userId.equals(customer.userId);
		}

		@Override
		public int hashCode() {
				return Objects.hash(id, name, surname, photo, userId);
		}

		@Override
		public String toString() {
				return "Customer{" +
						"id='" + id + '\'' +
						", name='" + name + '\'' +
						", surname='" + surname + '\'' +
						", photo='" + photo + '\'' +
						", userId='" + userId + '\'' +
						'}';
		}
}
