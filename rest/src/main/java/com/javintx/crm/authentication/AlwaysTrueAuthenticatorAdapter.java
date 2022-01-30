package com.javintx.crm.authentication;

import spark.Request;
import spark.Response;

public class AlwaysTrueAuthenticatorAdapter implements Authenticator {

	@Override
	public boolean isAuthenticated(final Request request, final Response response) {
		return true;
//		TODO: Add authentication method
//		if not authenticated --> halt(401, "You are not welcome here");
	}
}
