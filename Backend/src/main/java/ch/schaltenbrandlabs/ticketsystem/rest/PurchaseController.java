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
import ch.schaltenbrandlabs.ticketsystem.model.Purchase;
import ch.schaltenbrandlabs.ticketsystem.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

/**
 * The {@code PurchaseController} class handles HTTP requests related to the {@code Purchase} entity.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/purchase")
public class PurchaseController extends AbstractBaseController<Purchase> {

	private final PurchaseService purchaseService;

	/**
	 * Constructs a new {@code PurchaseController} with the provided {@code PurchaseService}.
	 *
	 * @param purchaseService the service to be used for performing purchase operations
	 */
	public PurchaseController(PurchaseService purchaseService) {
		super(purchaseService);
		this.purchaseService = purchaseService;
	}

	/**
	 * Creates a new purchase.
	 *
	 * @param entity the purchase entity to be created
	 * @return a {@code ResponseEntity} containing the created purchase with {@code HttpStatus.OK},
	 * or {@code HttpStatus.FORBIDDEN} if the purchase creation is not allowed
	 */
	@Override
	public ResponseEntity<Purchase> create(Purchase entity) {
		Purchase finalPurchase = purchaseService.create(entity);
		if (finalPurchase == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(finalPurchase, HttpStatus.OK);
	}

	/**
	 * Verifies a ticket using the token.
	 *
	 * @param request the request body containing the token parameter
	 * @return a {@code ResponseEntity} containing the verified purchase with {@code HttpStatus.OK},
	 * or {@code HttpStatus.CONFLICT} if the token verification fails,
	 * or {@code HttpStatus.NOT_FOUND} if the purchase is not found
	 */
	@PostMapping(value = "/verify", produces = {"application/json"}, consumes = {"application/json"})
	public ResponseEntity<Purchase> verifyTicket(@RequestBody Map<String, String> request) {
		try {
			Optional<Purchase> purchase = purchaseService.verifyTicket(request.get("token"));
			return purchase.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
					.orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
		} catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
