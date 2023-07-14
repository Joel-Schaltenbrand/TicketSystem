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

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * The {@code BaseController} interface defines the REST API endpoints for CRUD operations on a generic entity.
 *
 * @param <T> the type of the entity
 */

public interface BaseController<T> {

	/**
	 * Retrieves all entities.
	 *
	 * @return a {@code ResponseEntity} containing a list of entities with {@code HttpStatus.OK}
	 */
	@GetMapping("/get/all")
	ResponseEntity<List<T>> getAll();

	/**
	 * Retrieves an entity by its ID.
	 *
	 * @param id the ID of the entity
	 * @return a {@code ResponseEntity} containing the entity with {@code HttpStatus.OK},
	 * or {@code HttpStatus.NOT_FOUND} if the entity is not found
	 */
	@GetMapping("/get/{id}")
	ResponseEntity<T> getById(@PathVariable String id);

	/**
	 * Creates a new entity.
	 *
	 * @param entity the entity to be created
	 * @return a {@code ResponseEntity} containing the created entity with {@code HttpStatus.OK}
	 */
	@PostMapping("/create")
	ResponseEntity<T> create(@RequestBody T entity);

	/**
	 * Creates multiple entities.
	 *
	 * @param entities the list of entities to be created
	 * @return a {@code ResponseEntity} containing the list of created entities with {@code HttpStatus.OK}
	 */
	@PostMapping("/createMany")
	ResponseEntity<List<T>> createMany(@RequestBody List<T> entities);

	/**
	 * Updates an existing entity.
	 *
	 * @param updatedEntity the updated entity
	 * @return a {@code ResponseEntity} containing the updated entity with {@code HttpStatus.OK}
	 */
	@PutMapping("/update")
	ResponseEntity<T> update(@RequestBody T updatedEntity);

	/**
	 * Deletes an entity by its ID.
	 *
	 * @param id the ID of the entity to be deleted
	 * @return a {@code ResponseEntity} with {@code HttpStatus.OK} if the deletion is successful,
	 * or {@code HttpStatus.NOT_FOUND} if the entity is not found
	 */
	@DeleteMapping("/delete/{id}")
	ResponseEntity<Void> delete(@PathVariable String id);

	/**
	 * Deletes all entities.
	 *
	 * @return a {@code ResponseEntity} with {@code HttpStatus.OK}
	 */
	@DeleteMapping("/delete/all")
	ResponseEntity<Void> deleteAll();
}
