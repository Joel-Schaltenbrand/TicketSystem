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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code Ticket} class represents a ticket entity.
 *
 * <p>This class is annotated with {@code @Entity} to indicate that it is a persistent entity
 * mapped to a database table named "Tickets". It contains properties representing various
 * attributes of a ticket such as event, type, price, and quantity.
 *
 * <p>The class provides getters and setters for accessing and modifying the ticket properties.
 * It also includes a no-argument constructor and a parameterized constructor annotated with
 * {@code @AllArgsConstructor} and {@code @NoArgsConstructor} respectively.
 *
 * <p>The {@code Ticket} class establishes a Many-to-One relationship with the {@code Event} entity,
 * where multiple tickets can be associated with a single event.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Tickets")
public class Ticket {

	/**
	 * The ID of the ticket.
	 * It is annotated with {@code @Id} to indicate that it is the primary key.
	 * The ID is generated using the {@code GenerationType.UUID} strategy.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	/**
	 * The event associated with the ticket.
	 * It is annotated with {@code @ManyToOne} to establish a Many-to-One relationship
	 * with the {@code Event} entity.
	 */
	@ManyToOne
	private Event event;

	/**
	 * The type of the ticket.
	 */
	private String type;

	/**
	 * The price of the ticket.
	 */
	private double price;

	/**
	 * The quantity of tickets available.
	 */
	private int quantity;
}
