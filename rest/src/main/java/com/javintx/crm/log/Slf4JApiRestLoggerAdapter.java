package com.javintx.crm.log;

import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class Slf4JApiRestLoggerAdapter implements ApiRestLogger {

	private final org.slf4j.Logger log;

	public Slf4JApiRestLoggerAdapter(final Class<?> clazz) {
		log = LoggerFactory.getLogger(clazz);
	}

	public void request(final Request request, final Response response) {
		final String ip = request.ip();
		final String protocol = request.protocol();
		final String method = request.requestMethod();
		final String host = request.host();
		final String uri = request.uri();

		log.info("Request from {} --> {} {} {}{}", ip, protocol, method, host, uri);
	}

	@Override
	public void response(final Request request, final Response response) {
		final String ip = request.ip();
		final String host = request.host();
		final String uri = request.uri();

		final int status = response.status();
		final String type = response.type();

		log.info("Response for {} on {}{} --> {} {}", ip, host, uri, status, type);
	}

}
