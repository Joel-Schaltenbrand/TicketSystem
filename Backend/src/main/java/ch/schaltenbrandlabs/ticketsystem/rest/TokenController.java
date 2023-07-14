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

import ch.schaltenbrandlabs.ticketsystem.model.Token;
import ch.schaltenbrandlabs.ticketsystem.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The {@code TokenController} class handles HTTP requests related to the {@code Token} entity.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/token")
public class TokenController extends AbstractBaseController<Token> {

	private final TokenService tokenService;

	/**
	 * Constructs a new {@code TokenController} with the provided {@code TokenService}.
	 *
	 * @param tokenService the service to be used for performing token operations
	 */
	public TokenController(TokenService tokenService) {
		super(tokenService);
		this.tokenService = tokenService;
	}

	/**
	 * Invalidates a token.
	 *
	 * @param token the token to be invalidated
	 * @return a {@code ResponseEntity} containing the invalidated token with {@code HttpStatus.OK}
	 */
	@PostMapping(value = "/invalidate", produces = {"application/json"}, consumes = {"application/json"})
	public ResponseEntity<Token> invalidate(@RequestBody Token token) {
		return new ResponseEntity<>(tokenService.invalidate(token), HttpStatus.OK);
	}

	/**
	 * Creates a new token (not allowed).
	 *
	 * @param entity the token entity to be created
	 * @return a {@code ResponseEntity} with {@code HttpStatus.FORBIDDEN}
	 */
	@Override
	public ResponseEntity<Token> create(Token entity) {
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	/**
	 * Updates a token (not allowed).
	 *
	 * @param updatedEntity the updated token entity
	 * @return a {@code ResponseEntity} with {@code HttpStatus.FORBIDDEN}
	 */
	@Override
	public ResponseEntity<Token> update(Token updatedEntity) {
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	/**
	 * Deletes a token (not allowed).
	 *
	 * @param id the ID of the token to be deleted
	 * @return a {@code ResponseEntity} with {@code HttpStatus.FORBIDDEN}
	 */
	@Override
	public ResponseEntity<Void> delete(String id) {
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
}
