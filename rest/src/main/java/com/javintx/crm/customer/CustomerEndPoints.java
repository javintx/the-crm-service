package com.javintx.crm.customer;

public enum CustomerEndPoints {
	LIST_ALL_CUSTOMERS("/customer/all"),
	CREATE_NEW_CUSTOMER("/customer/create"),
	UPDATE_CUSTOMER("/customer/update");

	public final String uri;

	CustomerEndPoints(final String uri) {
		this.uri = uri;
	}
}
