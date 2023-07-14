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

package ch.schaltenbrandlabs.ticketsystem.service;

import ch.schaltenbrandlabs.ticketsystem.exeption.AlreadyExistsException;
import ch.schaltenbrandlabs.ticketsystem.exeption.NotFoundException;
import ch.schaltenbrandlabs.ticketsystem.model.Customer;
import ch.schaltenbrandlabs.ticketsystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The {@code CustomerService} class provides operations for managing customers.
 */
@Service
public class CustomerService extends AbstractBaseService<Customer> {

	private final CustomerRepository customerRepository;

	/**
	 * Constructs a new {@code CustomerService} with the provided {@code CustomerRepository}.
	 *
	 * @param customerRepository the repository to be used for performing customer operations
	 */
	@Autowired
	public CustomerService(CustomerRepository customerRepository) {
		super(customerRepository);
		this.customerRepository = customerRepository;
	}

	/**
	 * Creates a new customer.
	 *
	 * @param entity the customer entity to be created
	 * @return the created customer
	 * @throws AlreadyExistsException if a customer with the same email already exists
	 */
	@Override
	public Customer create(Customer entity) throws AlreadyExistsException {
		if (customerRepository.findByEmail(entity.getEmail()).isEmpty()) {
			return super.create(entity);
		}
		throw new AlreadyExistsException();
	}

	/**
	 * Finds a customer by email.
	 *
	 * @param email the email of the customer to be found
	 * @return the customer with the specified email
	 * @throws NotFoundException if the customer is not found
	 */
	public Customer findByEmail(String email) throws NotFoundException {
		Optional<Customer> customer = customerRepository.findByEmail(email);
		if (customer.isPresent()) {
			return customer.get();
		} else {
			throw new NotFoundException();
		}
	}
}
