package com.javintx.crm.port.out;

import com.javintx.crm.domain.Customer;

public interface CustomerWriter {

	Customer writes(Customer customer);
}
