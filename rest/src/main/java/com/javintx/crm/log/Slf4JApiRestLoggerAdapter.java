package com.javintx.crm.log;

import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import static java.lang.String.format;

public class Slf4JApiRestLoggerAdapter implements ApiRestLogger {

	private final org.slf4j.Logger log;

	public Slf4JApiRestLoggerAdapter(final Class<?> clazz) {
		log = LoggerFactory.getLogger(clazz);
	}

	public void apiCall(final Request request, final Response response) {
		log.info(format("Received api call: %s", request.uri()));
	}

}
