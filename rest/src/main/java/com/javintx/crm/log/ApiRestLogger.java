package com.javintx.crm.log;

import spark.Request;
import spark.Response;

public interface ApiRestLogger {

	/**
	 * Log a REST Api call.
	 *
	 * @param request  {@link Request}
	 * @param response {@link Response}
	 */
	void apiCall(final Request request, final Response response);

}
