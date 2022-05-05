package com.javintx.crm.application.springboot;

import com.javintx.crm.Arguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@ComponentScan({"com.javintx.crm"})
public class SpringBootApp {

		public SpringBootApp() {
				// Empty constructor that SpringBoot needs to start the application
		}

		public SpringBootApp(final String... args) {
				var application = new SpringApplication(SpringBootApp.class);
				application.setAddCommandLineProperties(false);

				Map<String, Object> props = new HashMap<>();
				props.put("server.port", Arguments.portFromOrDefault(args));

				application.setDefaultProperties(props);
				application.run(args);
		}

}
