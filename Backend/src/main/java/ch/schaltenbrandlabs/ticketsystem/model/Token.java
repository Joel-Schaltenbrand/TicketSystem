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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * The {@code Token} class represents a token entity.
 *
 * <p>This class is annotated with {@code @Entity} to indicate that it is a persistent entity
 * mapped to a database table named "Token". It contains properties representing various
 * attributes of a token such as verifyToken, isActive, and timestamp.
 *
 * <p>The class provides getters and setters for accessing and modifying the token properties.
 * It also includes a no-argument constructor and a parameterized constructor annotated with
 * {@code @AllArgsConstructor} and {@code @NoArgsConstructor} respectively.
 *
 * <p>The {@code Token} class includes an ID field annotated with {@code @Id} to indicate
 * it as the primary key. The ID is generated using the {@code GenerationType.UUID} strategy.
 *
 * <p>The class also includes the {@code isActive} field which represents the state of the token.
 * It is set to {@code true} by default. The {@code timestamp} field represents the timestamp
 * when the token was created and is annotated with {@code @Column(columnDefinition = "TIMESTAMP")}
 * to specify the column type as TIMESTAMP in the database.
 */
@Entity(name = "Token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Token {

	/**
	 * The ID of the token.
	 * It is annotated with {@code @Id} to indicate that it is the primary key.
	 * The ID is generated using the {@code GenerationType.UUID} strategy.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	/**
	 * The verification token.
	 */
	private String verifyToken;

	/**
	 * Indicates whether the token is active or not.
	 * It is set to {@code true} by default.
	 */
	private boolean isActive = true;

	/**
	 * The timestamp when the token was created.
	 * It is annotated with {@code @Column(columnDefinition = "TIMESTAMP")} to specify
	 * the column type as TIMESTAMP in the database.
	 */
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime timestamp = LocalDateTime.now();
}
