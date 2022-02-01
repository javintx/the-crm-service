package com.javintx.crm.customer;

import static com.javintx.crm.customer.CustomerEndPointsBindNames.CUSTOMER_ID;
import static java.lang.String.format;

public enum CustomerEndPoints {
	LIST_ALL_CUSTOMERS("/customer/all"),
	CREATE_NEW_CUSTOMER("/customer/create"),
	UPDATE_CUSTOMER("/customer/update"),
	DELETE_CUSTOMER(format("/customer/delete/:%s", CUSTOMER_ID.bindName));

	public final String uri;

	CustomerEndPoints(final String uri) {
		this.uri = uri;
	}
}
