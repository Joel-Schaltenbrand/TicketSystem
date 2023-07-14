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

import java.util.List;

/**
 * The {@code BaseService} interface defines the contract for common CRUD operations on a generic entity.
 *
 * @param <T> the type of the entity
 */
public interface BaseService<T> {

	/**
	 * Retrieves all entities.
	 *
	 * @return a list of entities
	 */
	List<T> getAll();

	/**
	 * Retrieves an entity by its ID.
	 *
	 * @param id the ID of the entity
	 * @return the entity with the specified ID
	 * @throws NotFoundException if the entity is not found
	 */
	T getById(String id) throws NotFoundException;

	/**
	 * Checks if an entity exists by its ID.
	 *
	 * @param id the ID of the entity
	 * @return {@code true} if the entity exists, {@code false} otherwise
	 */
	boolean existsById(String id);

	/**
	 * Creates a new entity.
	 *
	 * @param entity the entity to be created
	 * @return the created entity
	 * @throws AlreadyExistsException if the entity already exists
	 */
	T create(T entity) throws AlreadyExistsException;

	/**
	 * Creates multiple entities.
	 *
	 * @param entities the list of entities to be created
	 * @return the list of created entities
	 */
	List<T> createMany(List<T> entities);

	/**
	 * Updates an existing entity.
	 *
	 * @param updatedEntity the updated entity
	 * @return the updated entity
	 */
	T update(T updatedEntity);

	/**
	 * Deletes an entity by its ID.
	 *
	 * @param id the ID of the entity to be deleted
	 * @throws NotFoundException if the entity is not found
	 */
	void delete(String id) throws NotFoundException;

	/**
	 * Deletes all entities.
	 */
	void deleteAll();
}
