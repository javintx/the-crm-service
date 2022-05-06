package com.javintx.crm;

import org.junit.jupiter.api.Test;

import static com.javintx.crm.Arguments.APP_SERVER;
import static com.javintx.crm.Arguments.CREATE_ADMIN;
import static com.javintx.crm.Arguments.PORT;
import static com.javintx.crm.Arguments.SECRET;
import static com.javintx.crm.Arguments.createAdminOrDefault;
import static com.javintx.crm.Arguments.getAppOrDefault;
import static com.javintx.crm.Arguments.portFromOrDefault;
import static com.javintx.crm.Arguments.secretFromOrDefault;
import static org.assertj.core.api.Assertions.assertThat;

public class ArgumentsShould {

		private static final String ARGUMENT_HYPHEN = "-";
		private static final String INVALID_INPUT = "invalid";

		@Test
		void get_app_for_valid_input() {
				assertThat(getAppOrDefault(ARGUMENT_HYPHEN + APP_SERVER.getName(), Apps.SPRINGBOOT.getName())).isEqualTo(Apps.SPRINGBOOT);
		}

		@Test
		void get_default_app_for_invalid_input() {
				assertThat(getAppOrDefault(ARGUMENT_HYPHEN + APP_SERVER.getName(), INVALID_INPUT)).isEqualTo(Apps.SPARK);
		}

		@Test
		void get_default_app_for_no_input() {
				assertThat(getAppOrDefault()).isEqualTo(Apps.SPARK);
		}

		@Test
		void get_port_for_valid_input() {
				assertThat(portFromOrDefault(ARGUMENT_HYPHEN + PORT.getName(), "8081")).isEqualTo(8081);
		}

		@Test
		void get_default_port_for_invalid_input() {
				assertThat(portFromOrDefault(ARGUMENT_HYPHEN + PORT.getName(), INVALID_INPUT)).isEqualTo(8080);
		}

		@Test
		void get_default_port_for_no_input() {
				assertThat(portFromOrDefault()).isEqualTo(8080);
		}

		@Test
		void get_secret_for_valid_input() {
				assertThat(secretFromOrDefault(ARGUMENT_HYPHEN + SECRET.getName(), "cambiame")).isEqualTo("cambiame");
		}

		@Test
		void get_default_secret_for_no_input() {
				assertThat(secretFromOrDefault()).isEqualTo("changeIt");
		}

		@Test
		void get_create_admin_for_valid_input() {
				assertThat(createAdminOrDefault(ARGUMENT_HYPHEN + CREATE_ADMIN.getName(), "false")).isEqualTo(false);
		}

		@Test
		void get_create_admin_for_invalid_input() {
				assertThat(createAdminOrDefault(ARGUMENT_HYPHEN + CREATE_ADMIN.getName(), INVALID_INPUT)).isEqualTo(false);
		}

		@Test
		void get_default_create_admin_for_no_input() {
				assertThat(createAdminOrDefault()).isEqualTo(true);
		}
}
