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
				final var ip = request.ip();
				final var protocol = request.protocol();
				final var method = request.requestMethod();
				final var host = request.host();
				final var uri = request.uri();

				log.info("Request from {} --> {} {} {}{}", ip, protocol, method, host, uri);
		}

		@Override
		public void response(final Request request, final Response response) {
				final var ip = request.ip();
				final var host = request.host();
				final var uri = request.uri();

				final var status = response.status();
				final var type = response.type();

				log.info("Response for {} on {}{} --> {} {}", ip, host, uri, status, type);
		}

}
