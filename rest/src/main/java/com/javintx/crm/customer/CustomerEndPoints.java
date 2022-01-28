package com.javintx.crm.customer;

public enum CustomerEndPoints {
	LIST_ALL_CUSTOMERS("/customer/all");

	public final String uri;

	CustomerEndPoints(final String uri) {
		this.uri = uri;
	}
}
