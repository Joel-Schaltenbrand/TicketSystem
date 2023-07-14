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

package ch.schaltenbrandlabs.ticketsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code Purchase} class represents a purchase entity.
 *
 * <p>This class is annotated with {@code @Entity} to indicate that it is a persistent entity
 * mapped to a database table named "Purchases". It contains properties representing various
 * attributes of a purchase such as customer, ticket, and token.
 *
 * <p>The class provides getters and setters for accessing and modifying the purchase properties.
 * It also includes a no-argument constructor and a parameterized constructor annotated with
 * {@code @AllArgsConstructor} and {@code @NoArgsConstructor} respectively.
 *
 * <p>The {@code Purchase} class establishes the following relationships with other entities:
 * - Many-to-One relationship with the {@code Customer} entity
 * - Many-to-One relationship with the {@code Ticket} entity
 * - One-to-One relationship with the {@code Token} entity, with cascade deletion enabled
 */
@Entity(name = "Purchases")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Purchase {

	/**
	 * The ID of the purchase.
	 * It is annotated with {@code @Id} to indicate that it is the primary key.
	 * The ID is generated using the {@code GenerationType.UUID} strategy.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	/**
	 * The customer associated with the purchase.
	 * It is annotated with {@code @ManyToOne} to establish a many-to-one relationship
	 * with the {@code Customer} entity.
	 */
	@ManyToOne
	private Customer customer;

	/**
	 * The ticket associated with the purchase.
	 * It is annotated with {@code @ManyToOne} to establish a many-to-one relationship
	 * with the {@code Ticket} entity.
	 */
	@ManyToOne
	private Ticket ticket;

	/**
	 * The token associated with the purchase.
	 * It is annotated with {@code @OneToOne(cascade = jakarta.persistence.CascadeType.REMOVE)}
	 * to establish a one-to-one relationship with the {@code Token} entity, with cascade
	 * deletion enabled.
	 */
	@OneToOne(cascade = jakarta.persistence.CascadeType.REMOVE)
	private Token token;

	/**
	 * Constructs a new {@code Purchase} with the provided customer and ticket.
	 * The token is set to null initially.
	 *
	 * @param customer the customer associated with the purchase
	 * @param ticket   the ticket associated with the purchase
	 */
	public Purchase(Customer customer, Ticket ticket) {
		this.customer = customer;
		this.ticket = ticket;
		this.token = null;
	}
}
