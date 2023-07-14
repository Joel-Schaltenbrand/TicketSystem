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

import ch.schaltenbrandlabs.ticketsystem.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The {@code TokenRepository} interface provides CRUD operations and additional query methods
 * for the {@code Token} entity.
 *
 * <p>This interface extends the {@code JpaRepository} interface and specifies the {@code Token}
 * entity type and the type of its primary key, which is {@code String}.
 *
 * <p>In addition to the basic CRUD operations provided by {@code JpaRepository}, the
 * {@code TokenRepository} interface includes a custom query method {@code findByIsActiveFalse}
 * for retrieving a list of inactive tokens.
 *
 * <p>The {@code findByIsActiveFalse} method returns a list of tokens where the {@code isActive}
 * property is set to {@code false}.
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

	/**
	 * Retrieves a list of inactive tokens.
	 *
	 * @return a list of inactive tokens
	 */
	List<Token> findByIsActiveFalse();
}
