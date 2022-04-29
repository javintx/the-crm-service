package com.javintx.crm;

public class MainApp {

		private static final Apps DEFAULT_APP = Apps.SPARK;

		public static void main(final String... args) {
				getAppOrDefault(args).execute(args);
		}

		private static Apps getAppOrDefault(final String... args) {
				var app = DEFAULT_APP;
				if (args.length > 0) app = Apps.valueFrom(args[0]);
				return app;
		}

}

