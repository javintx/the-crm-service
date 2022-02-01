package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;

import java.util.Objects;

public class CustomerResponse {

		public final String id;
		public final String name;
		public final String surname;
		public final String photo;
		public final String userId;

		private CustomerResponse(final String id, final String name, final String surname, final String photo, final String userId) {
				this.id = id;
				this.name = name;
				this.surname = surname;
				this.photo = photo;
				this.userId = userId;
		}

		public static CustomerResponse from(final Customer customer) {
				return new CustomerResponse(customer.identifier(), customer.name(), customer.surname(), customer.photo(), customer.userReference());
		}

		@Override
		public boolean equals(Object o) {
				if (this == o) return true;
				if (o == null || getClass() != o.getClass()) return false;
				CustomerResponse that = (CustomerResponse) o;
				return id.equals(that.id) && name.equals(that.name) && surname.equals(that.surname) && Objects.equals(photo, that.photo) && userId.equals(that.userId);
		}

		@Override
		public int hashCode() {
				return Objects.hash(id, name, surname, photo, userId);
		}
}
