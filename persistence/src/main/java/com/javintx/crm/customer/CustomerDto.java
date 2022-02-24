package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;

public class CustomerDto {
		public final String id;
		public final String name;
		public final String surname;
		public final String photo;
		public final String userId;

		public CustomerDto(String id, String name, String surname, String photo, String userId) {
				this.id = id;
				this.name = name;
				this.surname = surname;
				this.photo = photo;
				this.userId = userId;
		}

		public static CustomerDto from(Customer customer) {
				return new CustomerDto(customer.identifier(), customer.name(), customer.surname(), customer.photo(), customer.userReference());
		}

		public Customer toDomain() {
				return new Customer(id, name, surname, photo, userId);
		}
}
