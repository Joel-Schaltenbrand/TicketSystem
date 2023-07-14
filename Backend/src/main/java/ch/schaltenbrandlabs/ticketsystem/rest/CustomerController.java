/*
 * MIT License
 *
 * Copyright (c) 2023 Joel Schaltenbrand.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ch.schaltenbrandlabs.ticketsystem.rest;

import ch.schaltenbrandlabs.ticketsystem.exeption.NotFoundException;
import ch.schaltenbrandlabs.ticketsystem.model.Customer;
import ch.schaltenbrandlabs.ticketsystem.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * The {@code CustomerController} class handles HTTP requests related to the {@code Customer} entity.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/customer")
public class CustomerController extends AbstractBaseController<Customer> {

	private final CustomerService customerService;

	/**
	 * Constructs a new {@code CustomerController} with the provided {@code CustomerService}.
	 *
	 * @param customerService the service to be used for performing customer operations
	 */
	public CustomerController(CustomerService customerService) {
		super(customerService);
		this.customerService = customerService;
	}

	/**
	 * Finds a customer by email.
	 *
	 * @param request the request body containing the email parameter
	 * @return a {@code ResponseEntity} containing the customer with {@code HttpStatus.OK},
	 * or {@code HttpStatus.NOT_FOUND} if the customer is not found
	 */
	@PostMapping(value = "/find/email", produces = {"application/json"}, consumes = {"application/json"})
	public ResponseEntity<Customer> findByEmail(@RequestBody Map<String, String> request) {
		try {
			return new ResponseEntity<>(customerService.findByEmail(request.get("email")), HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
