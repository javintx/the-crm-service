package com.javintx.crm.application.springboot;

import com.javintx.crm.user.UserRequest;
import com.javintx.crm.user.UserResponse;
import com.javintx.crm.user.UserUseCaseHandler;
import com.javintx.crm.user.exception.UserAlreadyExists;
import com.javintx.crm.user.exception.UserIsNotAdmin;
import com.javintx.crm.user.exception.UserNotExists;
import com.javintx.crm.user.exception.UserNotValid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.javintx.crm.application.springboot.SpringBootUserEndPoints.CREATE_NEW_USER;
import static com.javintx.crm.application.springboot.SpringBootUserEndPoints.DELETE_USER;
import static com.javintx.crm.application.springboot.SpringBootUserEndPoints.LIST_ALL_USERS;
import static com.javintx.crm.application.springboot.SpringBootUserEndPoints.UPDATE_USER;
import static com.javintx.crm.user.UserEndPoints.BindNames.USER_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserController {

		private final UserUseCaseHandler userUseCaseHandler;

		public UserController(final UserUseCaseHandler userUseCaseHandler) {
				this.userUseCaseHandler = userUseCaseHandler;
		}

		@GetMapping(value = LIST_ALL_USERS, produces = APPLICATION_JSON_VALUE)
		public List<UserResponse> listAllUsers() {
				return userUseCaseHandler.get();
		}

		@PostMapping(value = CREATE_NEW_USER, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
		public UserResponse createNewUser(@RequestBody final UserRequest userRequest) {
				return userUseCaseHandler.create(userRequest);
		}

		@PutMapping(value = UPDATE_USER, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
		public UserResponse updateUser(@PathVariable(value = USER_ID) final String identifier, @RequestBody final UserRequest userRequest) {
				return userUseCaseHandler.update(identifier, userRequest);
		}

		@DeleteMapping(DELETE_USER)
		public ResponseEntity<Void> deleteUser(@PathVariable(value = USER_ID) final String identifier) {
				userUseCaseHandler.delete(identifier);
				return ResponseEntity.ok().build();
		}

		@ResponseStatus(HttpStatus.CONFLICT)
		@ExceptionHandler(UserAlreadyExists.class)
		@ResponseBody
		ApiError handleException(final UserAlreadyExists ex) {
				return new ApiError(HttpStatus.CONFLICT, ex.getMessage());
		}

		@ResponseStatus(HttpStatus.FORBIDDEN)
		@ExceptionHandler(UserIsNotAdmin.class)
		@ResponseBody
		ApiError handleException(final UserIsNotAdmin ex) {
				return new ApiError(HttpStatus.FORBIDDEN, ex.getMessage());
		}

		@ResponseStatus(HttpStatus.NOT_FOUND)
		@ExceptionHandler(UserNotExists.class)
		@ResponseBody
		ApiError handleException(final UserNotExists ex) {
				return new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
		}

		@ResponseStatus(HttpStatus.BAD_REQUEST)
		@ExceptionHandler(UserNotValid.class)
		@ResponseBody
		ApiError handleException(final UserNotValid ex) {
				return new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
		}

}
