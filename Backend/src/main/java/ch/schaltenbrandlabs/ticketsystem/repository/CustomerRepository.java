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

package ch.schaltenbrandlabs.ticketsystem.repository;

import ch.schaltenbrandlabs.ticketsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The {@code CustomerRepository} interface provides CRUD operations and additional
 * query methods for the {@code Customer} entity.
 *
 * <p>This interface extends the {@code JpaRepository} interface and specifies the
 * {@code Customer} entity type and the type of its primary key, which is {@code String}.
 *
 * <p>In addition to the basic CRUD operations provided by {@code JpaRepository}, the
 * {@code CustomerRepository} interface includes a custom query method {@code findByEmail}
 * for retrieving a customer by their email.
 *
 * <p>The {@code findByEmail} method returns an {@code Optional} object that may contain
 * the customer with the specified email if found, or an empty {@code Optional} if not found.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

	/**
	 * Retrieves a customer by their email.
	 *
	 * @param email the email of the customer
	 * @return an {@code Optional} object that may contain the customer with the specified email
	 * if found, or an empty {@code Optional} if not found
	 */
	Optional<Customer> findByEmail(String email);
}
