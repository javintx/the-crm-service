package com.javintx.crm.log;

import spark.Request;
import spark.Response;

public interface ApiRestLogger {

		/**
			* Log a REST Api request.
			*
			* @param request  {@link Request} to be logged.
			* @param response {@link Response}.
			*/
		void request(final Request request, final Response response);

		/**
			* Log a REST Api response.
			*
			* @param request  {@link Request}.
			* @param response {@link Response} to be logged.
			*/
		void response(final Request request, final Response response);
}
