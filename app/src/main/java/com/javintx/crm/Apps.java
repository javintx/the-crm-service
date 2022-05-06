package com.javintx.crm;

import com.javintx.crm.application.sparkjava.SparkJavaApp;
import com.javintx.crm.application.springboot.SpringBootApp;

import java.util.function.Consumer;

import static java.util.Arrays.stream;

public enum Apps {
		SPARK("spark", SparkJavaApp::new), SPRINGBOOT("springboot", SpringBootApp::new);

		private final String name;
		private final Consumer<String[]> executor;

		Apps(final String name, final Consumer<String[]> executor) {
				this.name = name;
				this.executor = executor;
		}

		public static Apps valueFrom(final String appName, final Apps defaultApp) {
				return stream(Apps.values())
						.filter(apps -> apps.name.equalsIgnoreCase(appName))
						.findFirst()
						.orElse(defaultApp);
		}

		public String getName() {
				return name;
		}

		void execute(final String... args) {
				executor.accept(args);
		}

}
