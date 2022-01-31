package com.javintx.crm.domain;

import java.util.Objects;

public final class Customer {

	private final String id;
	private final String name;
	private final String surname;
	private final String photo;

	public Customer(String id, String name, String surname) {
		this(id, name, surname, null);
	}

	public Customer(String id, String name, String surname, String photo) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.photo = photo;
	}

	public String identifier() {
		return id;
	}

	public String name() {
		return name;
	}

	public String surname() {
		return surname;
	}

	public String photo() {
		return photo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return id.equals(customer.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
