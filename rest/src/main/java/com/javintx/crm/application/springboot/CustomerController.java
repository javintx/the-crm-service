package com.javintx.crm.application.springboot;

import com.javintx.crm.customer.CustomerRequest;
import com.javintx.crm.customer.CustomerResponse;
import com.javintx.crm.customer.CustomerUseCaseHandler;
import com.javintx.crm.customer.exception.CustomerAlreadyExists;
import com.javintx.crm.customer.exception.CustomerNotExists;
import com.javintx.crm.customer.exception.CustomerNotValid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.javintx.crm.customer.CustomerEndPoints.BindNames.CUSTOMER_ID;
import static com.javintx.crm.customer.CustomerEndPoints.CREATE_NEW_CUSTOMER;
import static com.javintx.crm.customer.CustomerEndPoints.DELETE_CUSTOMER;
import static com.javintx.crm.customer.CustomerEndPoints.LIST_ALL_CUSTOMERS;
import static com.javintx.crm.customer.CustomerEndPoints.UPDATE_CUSTOMER;

@RestController
public class CustomerController {

		private final CustomerUseCaseHandler customerUseCaseHandler;

		public CustomerController(final CustomerUseCaseHandler customerUseCaseHandler) {
				this.customerUseCaseHandler = customerUseCaseHandler;
		}

		@GetMapping(LIST_ALL_CUSTOMERS)
		public List<CustomerResponse> listAllCustomers() {
				return customerUseCaseHandler.get();
		}

		@PostMapping(CREATE_NEW_CUSTOMER)
		public CustomerResponse createNewUser(@RequestBody final CustomerRequest customerRequest) {
				return customerUseCaseHandler.create(customerRequest);
		}

		@PutMapping(UPDATE_CUSTOMER)
		public CustomerResponse updateUser(@RequestBody final CustomerRequest customerRequest) {
				return customerUseCaseHandler.update(customerRequest);
		}

		@DeleteMapping(DELETE_CUSTOMER)
		public ResponseEntity<Void> delete(@RequestParam(value = CUSTOMER_ID) final String identifier) {
				customerUseCaseHandler.delete(identifier);
				return ResponseEntity.ok().build();
		}

		@ResponseStatus(HttpStatus.CONFLICT)
		@ExceptionHandler(CustomerAlreadyExists.class)
		@ResponseBody
		ApiError handleException(final CustomerAlreadyExists ex) {
				return new ApiError(HttpStatus.CONFLICT, ex.getMessage());
		}

		@ResponseStatus(HttpStatus.NOT_FOUND)
		@ExceptionHandler(CustomerNotExists.class)
		@ResponseBody
		ApiError handleException(final CustomerNotExists ex) {
				return new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
		}

		@ResponseStatus(HttpStatus.BAD_REQUEST)
		@ExceptionHandler(CustomerNotValid.class)
		@ResponseBody
		ApiError handleException(final CustomerNotValid ex) {
				return new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
}
