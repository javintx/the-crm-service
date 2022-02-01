package com.javintx.crm.application;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javintx.crm.user.UserRequest;
import com.javintx.crm.user.UserResponse;
import com.javintx.crm.user.UserUseCaseHandler;
import com.javintx.crm.user.exception.UserAlreadyExists;
import com.javintx.crm.user.exception.UserNotExists;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.javintx.crm.user.UserEndPoints.CREATE_NEW_USER;
import static com.javintx.crm.user.UserEndPoints.DELETE_USER;
import static com.javintx.crm.user.UserEndPoints.LIST_ALL_USERS;
import static com.javintx.crm.user.UserEndPoints.UPDATE_USER;
import static com.javintx.crm.user.UserEndPointsBindNames.USER_ID;
import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.eclipse.jetty.http.MimeTypes.Type.APPLICATION_JSON;
import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

public class UserController {

		private final UserUseCaseHandler userUseCaseHandler;
		private final ObjectMapper objectMapper;

		public UserController(final UserUseCaseHandler userUseCaseHandler) {
				this.userUseCaseHandler = userUseCaseHandler;
				objectMapper = new ObjectMapper();
				objectMapper.configure(FAIL_ON_EMPTY_BEANS, false);
				routes();
		}

		private void routes() {
				get(LIST_ALL_USERS.uri, "*/*", this::handleListAllUsers, objectMapper::writeValueAsString);
				post(CREATE_NEW_USER.uri, APPLICATION_JSON.asString(), this::handleCreateNewUser, objectMapper::writeValueAsString);
				put(UPDATE_USER.uri, APPLICATION_JSON.asString(), this::handleUpdateUser, objectMapper::writeValueAsString);
				delete(DELETE_USER.uri, "*/*", this::handleDeleteUser, objectMapper::writeValueAsString);
				exceptions();
		}

		private void exceptions() {
				exception(UserNotExists.class, (e, request, response) -> {
						response.status(SC_NOT_FOUND);
						response.body(e.getMessage());
				});
				exception(UserAlreadyExists.class, (e, request, response) -> {
						response.status(SC_CONFLICT);
						response.body(e.getMessage());
				});
		}

		private List<UserResponse> handleListAllUsers(final Request request, final Response response) {
				return userUseCaseHandler.get();
		}

		private UserResponse handleCreateNewUser(final Request request, final Response response) throws IOException {
				try (JsonParser parser = objectMapper.createParser(request.body())) {
						return userUseCaseHandler.create(parser.readValueAs(UserRequest.class));
				}
		}

		private UserResponse handleUpdateUser(final Request request, final Response response) throws IOException {
				try (JsonParser parser = objectMapper.createParser(request.body())) {
						return userUseCaseHandler.update(parser.readValueAs(UserRequest.class));
				}
		}

		private String handleDeleteUser(final Request request, final Response response) {
				userUseCaseHandler.delete(request.params(USER_ID.bindName));
				response.status(SC_OK);
				return "OK";
		}
}