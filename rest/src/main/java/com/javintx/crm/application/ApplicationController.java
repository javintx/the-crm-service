package com.javintx.crm.application;

import com.javintx.crm.authentication.Authenticator;
import com.javintx.crm.log.ApiRestLogger;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.options;
import static spark.Spark.port;

public class ApplicationController {

		private final Authenticator authenticator;
		private final ApiRestLogger log;

		public ApplicationController(int portNumber, final Authenticator authenticator, final ApiRestLogger log) {
				this.authenticator = authenticator;
				this.log = log;
				setupServer(portNumber);
		}

		private void setupServer(final int portNumber) {
				port(portNumber);
				routes();
		}

		private void routes() {
				enableCORS();
				before(log::request);
				before(authenticator::isAuthenticated);
				after(log::response);
		}

		private void enableCORS() {
				options("/*", (request, response) -> {
						String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
						if (accessControlRequestHeaders != null) {
								response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
						}

						String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
						if (accessControlRequestMethod != null) {
								response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
						}

						return "OK";
				});

				// These values could be modified if necessary
				before((request, response) -> {
						response.header("Access-Control-Allow-Origin", "*");
						response.header("Access-Control-Request-Method", "GET,PUT,POST,DELETE,OPTIONS");
						response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
						response.header("Access-Control-Allow-Credentials", "true");
				});
		}
}
