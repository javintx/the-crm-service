package com.javintx.crm.log;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class Slf4JApiRestLoggerAdapterShould {

	@Test
	void log_api_call_is_logged_when_has_request() {
		ApiRestLogger logger = new Slf4JApiRestLoggerAdapter(Slf4JApiRestLoggerAdapterShould.class);

		Request request = mock(Request.class);
		Response response = mock(Response.class);

		logger.request(request, response);

		verify(request).uri();
	}

}
