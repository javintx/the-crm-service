package com.javintx.crm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppsShould {

		@Test
		void return_app_for_valid_app_name() {
				assertThat(Apps.valueFrom(Apps.SPRINGBOOT.getName(), Apps.SPARK)).isEqualTo(Apps.SPRINGBOOT);
		}

		@Test
		void return_default_value_for_invalid_app_name() {
				assertThat(Apps.valueFrom("invalid", Apps.SPARK)).isEqualTo(Apps.SPARK);
		}
}
