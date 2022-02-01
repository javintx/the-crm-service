package com.javintx.crm.customer.impl;

import com.javintx.crm.customer.ListAllCustomers;
import com.javintx.crm.domain.Customer;
import com.javintx.crm.port.out.customer.CustomerReader;

import java.util.List;

public class ListAllCustomersService implements ListAllCustomers {

		private final CustomerReader customerReader;

		public ListAllCustomersService(final CustomerReader customerReader) {
				this.customerReader = customerReader;
		}

		@Override
		public List<Customer> get() {
				return customerReader.readAll();
		}

}
