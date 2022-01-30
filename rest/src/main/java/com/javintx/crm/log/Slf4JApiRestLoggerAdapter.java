package com.javintx.crm.log;

import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class Slf4JApiRestLoggerAdapter implements ApiRestLogger {

	private final org.slf4j.Logger log;

	public Slf4JApiRestLoggerAdapter(final Class<?> clazz) {
		log = LoggerFactory.getLogger(clazz);
	}

	public void apiCall(final Request request, final Response response) {
		final String message = request.uri();
		log.info("Received api call: {}", message);
	}

}
