package com.javintx.crm.customer;

public enum CustomerEndPointsBindNames {
	CUSTOMER_ID("customerId");

	public final String bindName;

	CustomerEndPointsBindNames(final String bindName) {
		this.bindName = bindName;
	}
}
