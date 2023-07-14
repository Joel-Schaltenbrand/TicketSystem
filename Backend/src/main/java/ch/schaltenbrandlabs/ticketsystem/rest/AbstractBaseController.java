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

import ch.schaltenbrandlabs.ticketsystem.exeption.AlreadyExistsException;
import ch.schaltenbrandlabs.ticketsystem.exeption.NotFoundException;
import ch.schaltenbrandlabs.ticketsystem.service.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * The {@code AbstractBaseController} class is an abstract base class that implements the
 * {@code BaseController} interface. It provides common CRUD operations for a generic entity.
 *
 * @param <T> the type of the entity
 */
public abstract class AbstractBaseController<T> implements BaseController<T> {
	private final BaseService<T> service;

	/**
	 * Constructs a new {@code AbstractBaseController} with the provided {@code BaseService}.
	 *
	 * @param service the service to be used for performing operations on the entity
	 */
	protected AbstractBaseController(BaseService<T> service) {
		this.service = service;
	}

	/**
	 * Retrieves all entities.
	 *
	 * @return a {@code ResponseEntity} containing a list of entities with {@code HttpStatus.OK}
	 */
	@Override
	public ResponseEntity<List<T>> getAll() {
		List<T> entities = service.getAll();
		return new ResponseEntity<>(entities, HttpStatus.OK);
	}

	/**
	 * Retrieves an entity by its ID.
	 *
	 * @param id the ID of the entity
	 * @return a {@code ResponseEntity} containing the entity with {@code HttpStatus.OK},
	 * or {@code HttpStatus.NOT_FOUND} if the entity is not found
	 */
	@Override
	public ResponseEntity<T> getById(String id) {
		try {
			T entity = service.getById(id);
			return new ResponseEntity<>(entity, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Creates a new entity.
	 *
	 * @param entity the entity to be created
	 * @return a {@code ResponseEntity} containing the created entity with {@code HttpStatus.OK},
	 * or {@code HttpStatus.CONFLICT} if there is a conflict during creation
	 */
	@Override
	public ResponseEntity<T> create(T entity) {
		try {
			return new ResponseEntity<>(service.create(entity), HttpStatus.OK);
		} catch (AlreadyExistsException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/**
	 * Creates multiple entities.
	 *
	 * @param entities the list of entities to be created
	 * @return a {@code ResponseEntity} containing the list of created entities with {@code HttpStatus.OK}
	 */
	@Override
	public ResponseEntity<List<T>> createMany(List<T> entities) {
		return new ResponseEntity<>(service.createMany(entities), HttpStatus.OK);
	}

	/**
	 * Updates an existing entity.
	 *
	 * @param updatedEntity the updated entity
	 * @return a {@code ResponseEntity} containing the updated entity with {@code HttpStatus.OK}
	 */
	@Override
	public ResponseEntity<T> update(T updatedEntity) {
		return new ResponseEntity<>(service.update(updatedEntity), HttpStatus.OK);
	}

	/**
	 * Deletes an entity by its ID.
	 *
	 * @param id the ID of the entity to be deleted
	 * @return a {@code ResponseEntity} with {@code HttpStatus.OK} if the deletion is successful,
	 * or {@code HttpStatus.NOT_FOUND} if the entity is not found
	 */
	@Override
	public ResponseEntity<Void> delete(String id) {
		try {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Deletes all entities.
	 *
	 * @return a {@code ResponseEntity} with {@code HttpStatus.OK}
	 */
	@Override
	public ResponseEntity<Void> deleteAll() {
		service.deleteAll();
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
