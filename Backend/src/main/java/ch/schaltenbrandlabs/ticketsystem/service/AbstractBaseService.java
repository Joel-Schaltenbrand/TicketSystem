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
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * The {@code AbstractBaseService} class is an abstract base class that implements the {@code BaseService} interface.
 * It provides common CRUD operations for a generic entity using a {@code JpaRepository}.
 *
 * @param <T> the type of the entity
 */
public abstract class AbstractBaseService<T> implements BaseService<T> {
	private final JpaRepository<T, String> repository;

	/**
	 * Constructs a new {@code AbstractBaseService} with the provided {@code JpaRepository}.
	 *
	 * @param repository the repository to be used for performing operations on the entity
	 */
	protected AbstractBaseService(JpaRepository<T, String> repository) {
		this.repository = repository;
	}

	/**
	 * Retrieves all entities.
	 *
	 * @return a list of entities
	 */
	@Override
	public List<T> getAll() {
		return repository.findAll();
	}

	/**
	 * Retrieves an entity by its ID.
	 *
	 * @param id the ID of the entity
	 * @return the entity with the specified ID
	 * @throws NotFoundException if the entity is not found
	 */
	@Override
	public T getById(String id) throws NotFoundException {
		Optional<T> entity = repository.findById(id);
		if (entity.isEmpty()) {
			throw new NotFoundException();
		}
		return entity.get();
	}

	/**
	 * Checks if an entity exists by its ID.
	 *
	 * @param id the ID of the entity
	 * @return {@code true} if the entity exists, {@code false} otherwise
	 */
	@Override
	public boolean existsById(String id) {
		return repository.existsById(id);
	}

	/**
	 * Creates a new entity.
	 *
	 * @param entity the entity to be created
	 * @return the created entity
	 * @throws AlreadyExistsException if the entity already exists
	 */
	@Override
	public T create(T entity) throws AlreadyExistsException {
		return repository.save(entity);
	}

	/**
	 * Creates multiple entities.
	 *
	 * @param entities the list of entities to be created
	 * @return the list of created entities
	 */
	@Override
	public List<T> createMany(List<T> entities) {
		return repository.saveAll(entities);
	}

	/**
	 * Updates an existing entity.
	 *
	 * @param updatedEntity the updated entity
	 * @return the updated entity
	 */
	@Override
	public T update(T updatedEntity) {
		return repository.save(updatedEntity);
	}

	/**
	 * Deletes an entity by its ID.
	 *
	 * @param id the ID of the entity to be deleted
	 * @throws NotFoundException if the entity is not found
	 */
	@Override
	public void delete(String id) throws NotFoundException {
		repository.delete(getById(id));
	}

	/**
	 * Deletes all entities.
	 */
	@Override
	public void deleteAll() {
		repository.deleteAll();
	}
}
