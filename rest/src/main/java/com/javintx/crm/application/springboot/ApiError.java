package com.javintx.crm.application.springboot;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

class ApiError {
		@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
		private final LocalDateTime timestamp = LocalDateTime.now();
		@JsonFormat
		private final HttpStatus status;
		@JsonFormat
		private final String message;

		ApiError(final HttpStatus status, final String message) {
				this.status = status;
				this.message = message;
		}

}
