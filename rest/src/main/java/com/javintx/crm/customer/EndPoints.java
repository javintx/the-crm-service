package com.javintx.crm.customer;

public enum EndPoints {
    LIST_ALL_CUSTOMERS("/customer/all");

    public final String uri;

    EndPoints(final String uri) {
        this.uri = uri;
    }
}
