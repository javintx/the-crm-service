package com.javintx.crm;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Optional;

public enum Arguments {

		APP_SERVER(
				"as",
				Option.builder()
						.option("as")
						.longOpt("app_server")
						.numberOfArgs(1)
						.required(false)
						.type(String.class)
						.desc("The application server")
						.build()),
		PORT(
				"p",
				Option.builder()
						.option("p")
						.longOpt("port")
						.numberOfArgs(1)
						.required(false)
						.type(Integer.class)
						.desc("The port number")
						.build()),
		SECRET(
				"s",
				Option.builder()
						.option("s")
						.longOpt("port")
						.numberOfArgs(1)
						.required(false)
						.type(String.class)
						.desc("The secret for the JWT")
						.build()),
		CREATE_ADMIN(
				"ca",
				Option.builder()
						.option("ca")
						.longOpt("create_admin")
						.numberOfArgs(1)
						.required(false)
						.type(Boolean.class)
						.desc("Flag to create a default admin user")
						.build());

		public static final Options ALL_ARGUMENTS = new Options()
				.addOption(APP_SERVER.option)
				.addOption(PORT.option)
				.addOption(SECRET.option)
				.addOption(CREATE_ADMIN.option);

		private final String name;
		private final Option option;

		Arguments(final String name, final Option option) {
				this.name = name;
				this.option = option;
		}

		private String parse(final String... args) {
				try {
						return DefaultParser.builder()
								.setAllowPartialMatching(false)
								.build()
								.parse(ALL_ARGUMENTS, args)
								.getOptionValue(name);
				} catch (ParseException e) {
						new HelpFormatter().printHelp("AppMain", ALL_ARGUMENTS, true);
						System.err.println("Unknown argument: " + e.getMessage());
						return null;
				}
		}

		public static Apps getAppOrDefault(final String... args) {
				return Apps.valueFrom(APP_SERVER.parse(args), Apps.SPARK);
		}

		public static int portFromOrDefault(final String... args) {
				return Integer.parseInt(Optional.ofNullable(PORT.parse(args)).orElse("8080"));
		}

		public static String secretFromOrDefault(final String... args) {
				return Optional.ofNullable(SECRET.parse(args)).orElse("changeIt");
		}

		public static boolean createAdminOrDefault(final String[] args) {
				return Boolean.parseBoolean(Optional.ofNullable(CREATE_ADMIN.parse(args)).orElse("true"));
		}

}
