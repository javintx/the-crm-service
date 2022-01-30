package com.javintx.crm.authentication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AlwaysTrueAuthenticatorAdapterShould {

	@Test
	void return_true_is_request_is_authenticated() {
		Authenticator authenticator = new AlwaysTrueAuthenticatorAdapter();

		Request request = mock(Request.class);
		Response response = mock(Response.class);

		boolean isAuthenticated = authenticator.isAuthenticated(request, response);
		assertTrue(isAuthenticated);
	}
}
