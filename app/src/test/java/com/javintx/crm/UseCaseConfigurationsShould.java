package com.javintx.crm;

import com.javintx.crm.application.UseCaseConfigurations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UseCaseConfigurationsShould {
		@Test
		void return_valid_user_use_case_handler() {
				assertNotNull(UseCaseConfigurations.initializeUserUseCaseHandler());
		}

		@Test
		void return_valid_customer_use_case_handler() {
				assertNotNull(UseCaseConfigurations.initializeCustomerUseCaseHandler());
		}
}
