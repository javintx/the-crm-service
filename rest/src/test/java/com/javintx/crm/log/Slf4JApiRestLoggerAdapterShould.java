package com.javintx.crm.log;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class Slf4JApiRestLoggerAdapterShould {

		@Test
		void request_is_logged() {
				ApiRestLogger logger = new Slf4JApiRestLoggerAdapter(Slf4JApiRestLoggerAdapterShould.class);

				Request request = mock(Request.class);
				Response response = mock(Response.class);

				logger.request(request, response);

				verify(request).ip();
				verify(request).protocol();
				verify(request).requestMethod();
				verify(request).host();
				verify(request).uri();
		}

		@Test
		void response_is_logged() {
				ApiRestLogger logger = new Slf4JApiRestLoggerAdapter(Slf4JApiRestLoggerAdapterShould.class);

				Request request = mock(Request.class);
				Response response = mock(Response.class);

				logger.response(request, response);

				verify(request).ip();
				verify(request).host();
				verify(request).uri();

				verify(response).status();
				verify(response).type();
		}

}
