package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;

import java.util.Objects;

public class CustomerResponse {

		public final String id;
		public final String name;
		public final String surname;
		public final String photo;

		private CustomerResponse(final String id, final String name, final String surname, final String photo) {
				this.id = id;
				this.name = name;
				this.surname = surname;
				this.photo = photo;
		}

		public static CustomerResponse from(final Customer customer) {
				return new CustomerResponse(customer.identifier(), customer.name(), customer.surname(), customer.photo());
		}

		@Override
		public boolean equals(Object o) {
				if (this == o) return true;
				if (o == null || getClass() != o.getClass()) return false;
				CustomerResponse that = (CustomerResponse) o;
				return id.equals(that.id);
		}

		@Override
		public int hashCode() {
				return Objects.hash(id);
		}
}
