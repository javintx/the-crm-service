package com.javintx.crm.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;

@SpringBootApplication
@ComponentScan({ "com.javintx.crm" })
public class SpringBootApp {

		private static final int STANDARD_PORT = 8080;

		public static void main(final String[] args) {
				SpringApplication application = new SpringApplication(SpringBootApp.class);
				application.setAddCommandLineProperties(false);

				HashMap<String, Object> props = new HashMap<>();
				props.put("server.port", portFromOrDefault(args));

				application.setDefaultProperties(props);
				application.run(args);
		}

		private static int portFromOrDefault(final String[] args) {
				var port = STANDARD_PORT;
				if (args.length > 0)
						port = Integer.parseInt(args[0]);
				return port;
		}
}
