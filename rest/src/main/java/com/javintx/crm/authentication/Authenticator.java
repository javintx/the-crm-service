package com.javintx.crm.authentication;

import spark.Request;
import spark.Response;

public interface Authenticator {

		/**
			* Verify if the request is authenticated.
			*
			* @param request  {@link Request}
			* @param response {@link Response}
			* @return True only if the request is authenticated.
			*/
		boolean isAuthenticated(final Request request, final Response response);
}
