package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerWriter;

public class CustomerWriterInMemoryAdapter implements CustomerWriter {

		@Override
		public Customer writes(final Customer customer) {
				var customerDto = CustomerDto.from(customer);
				InMemoryStorage.INSTANCE.customers().put(customerDto.identifier(), customerDto);
				return customer;
		}
}
