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

import ch.schaltenbrandlabs.ticketsystem.model.Purchase;
import ch.schaltenbrandlabs.ticketsystem.model.Token;
import ch.schaltenbrandlabs.ticketsystem.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

/**
 * The {@code TokenService} class provides operations for managing tokens.
 */
@Service
public class TokenService extends AbstractBaseService<Token> {
	private static final String HMAC_ALGORITHM = "HmacSHA256";

	private final TokenRepository tokenRepository;

	@Value("${secret.key}")
	private String secretKey;

	/**
	 * Constructs a new {@code TokenService} with the provided {@code TokenRepository}.
	 *
	 * @param tokenRepository the repository to be used for performing token operations
	 */
	public TokenService(TokenRepository tokenRepository) {
		super(tokenRepository);
		this.tokenRepository = tokenRepository;
	}

	/**
	 * Deletes all inactive tokens older than one week.
	 */
	@Override
	public void deleteAll() {
		List<Token> inactiveTokens = tokenRepository.findByIsActiveFalse();
		for (Token token : inactiveTokens) {
			if (token.getTimestamp().isBefore(LocalDateTime.now().minusWeeks(1))) {
				tokenRepository.delete(token);
			}
		}
	}

	/**
	 * Generates a token for the given purchase.
	 *
	 * @param purchase the purchase for which to generate the token
	 * @return the generated token, or {@code null} if the HMAC generation fails
	 */
	public Token generateToken(Purchase purchase) {
		String ticketData = purchase.getId();
		String hmac = generateHmac(ticketData, secretKey);
		if (hmac == null) {
			return null;
		}
		Token token = new Token();
		token.setVerifyToken(String.format("%s:%s", ticketData, hmac));
		tokenRepository.save(token);
		return token;
	}

	/**
	 * Verifies the authenticity of a token.
	 *
	 * @param tokenParts the token parts to be verified (purchase ID and received HMAC)
	 * @return {@code true} if the token is valid, {@code false} otherwise
	 */
	public boolean verifyToken(String[] tokenParts) {
		String purchaseId = tokenParts[0];
		String receivedHmac = tokenParts[1];
		return verifyHmac(purchaseId, receivedHmac, secretKey);
	}

	private boolean verifyHmac(String data, String hmac, String secretKey) {
		String calculatedHmac = generateHmac(data, secretKey);
		return calculatedHmac != null && calculatedHmac.equals(hmac);
	}

	private String generateHmac(String data, String secretKey) {
		try {
			Mac mac = Mac.getInstance(HMAC_ALGORITHM);
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
			mac.init(secretKeySpec);
			byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(hmacBytes);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			return null;
		}
	}

	/**
	 * Invalidates a token by setting it as inactive and updating the timestamp.
	 *
	 * @param token the token to be invalidated
	 * @return the invalidated token
	 */
	public Token invalidate(Token token) {
		token.setActive(false);
		token.setTimestamp(LocalDateTime.now());
		return tokenRepository.save(token);
	}
}
