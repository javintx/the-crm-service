package com.javintx.crm.customer;

public enum CustomerEndPoints {
	LIST_ALL_CUSTOMERS("/customer/all"),
	CREATE_NEW_CUSTOMER("/customer/create");

	public final String uri;

	CustomerEndPoints(final String uri) {
		this.uri = uri;
	}
}
