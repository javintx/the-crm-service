package com.javintx.crm.usecase.exception;

import static java.lang.String.format;

public class CustomerNotExists extends RuntimeException {

		public CustomerNotExists(final String customerId) {
				super(format("Customer identified with %s does not exists", customerId));
		}

}
