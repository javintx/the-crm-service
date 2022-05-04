package com.javintx.crm.authentication;

import spark.Request;

public interface Authenticator {

		/**
			* Verify if the request is authenticated.
			*
			* @param authorizationHeader {@link Request}
			* @return True only if the request is authenticated.
			*/
		boolean isAuthenticated(final String authorizationHeader);

}
