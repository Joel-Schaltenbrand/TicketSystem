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

import ch.schaltenbrandlabs.ticketsystem.exeption.NotFoundException;
import ch.schaltenbrandlabs.ticketsystem.model.Customer;
import ch.schaltenbrandlabs.ticketsystem.model.Purchase;
import ch.schaltenbrandlabs.ticketsystem.model.Ticket;
import ch.schaltenbrandlabs.ticketsystem.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * The {@code PurchaseService} class provides operations for managing purchases.
 */
@Service
public class PurchaseService extends AbstractBaseService<Purchase> {

	private final CustomerService customerService;
	private final PurchaseRepository purchaseRepository;
	private final TokenService tokenService;
	private final TicketService ticketService;

	/**
	 * Constructs a new {@code PurchaseService} with the provided dependencies.
	 *
	 * @param customerService    the service for managing customers
	 * @param purchaseRepository the repository for accessing purchases
	 * @param tokenService       the service for managing tokens
	 * @param ticketService      the service for managing tickets
	 */
	@Autowired
	public PurchaseService(CustomerService customerService, PurchaseRepository purchaseRepository,
				TokenService tokenService, TicketService ticketService) {
		super(purchaseRepository);
		this.customerService = customerService;
		this.purchaseRepository = purchaseRepository;
		this.tokenService = tokenService;
		this.ticketService = ticketService;
	}

	/**
	 * Creates a new purchase.
	 *
	 * @param entity the purchase entity to be created
	 * @return the created purchase, or {@code null} if the creation fails due to a not found exception
	 */
	@Override
	public Purchase create(Purchase entity) {
		try {
			Ticket ticket = ticketService.getById(entity.getTicket().getId());
			if (ticket.getQuantity() <= 0) {
				return null;
			}
			Customer customer = customerService.getById(entity.getCustomer().getId());
			Purchase purchased = purchaseRepository.save(new Purchase(customer, ticket));
			purchased.setToken(tokenService.generateToken(purchased));
			ticket.setQuantity(ticket.getQuantity() - 1);
			ticketService.update(ticket);
			return purchaseRepository.save(purchased);
		} catch (NotFoundException e) {
			return null;
		}
	}

	/**
	 * Verifies a ticket using the token.
	 *
	 * @param token the token to be verified
	 * @return an optional containing the verified purchase if the token is valid,
	 * or an empty optional if the token is invalid
	 * @throws NotFoundException if the purchase is not found
	 */
	public Optional<Purchase> verifyTicket(String token) throws NotFoundException {
		String[] tokenParts = token.split(":");
		if (tokenParts.length == 2) {
			Purchase purchase = getById(tokenParts[0]);
			if (verifyDatabase(purchase) && tokenService.verifyToken(tokenParts)) {
				return Optional.of(purchase);
			}
		}
		return Optional.empty();
	}

	/**
	 * Verifies if the purchase data exists in the database.
	 *
	 * @param purchase the purchase to be verified
	 * @return {@code true} if the purchase data exists in the database, {@code false} otherwise
	 */
	private boolean verifyDatabase(Purchase purchase) {
		return ticketService.existsById(purchase.getTicket().getId())
				&& customerService.existsById(purchase.getCustomer().getId());
	}
}
