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

import ch.schaltenbrandlabs.ticketsystem.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The {@code EventRepository} interface provides CRUD operations and additional query methods
 * for the {@code Event} entity.
 *
 * <p>This interface extends the {@code JpaRepository} interface and specifies the
 * {@code Event} entity type and the type of its primary key, which is {@code String}.
 *
 * <p>In addition to the basic CRUD operations provided by {@code JpaRepository}, the
 * {@code EventRepository} interface includes custom query methods for searching events
 * by their title and description.
 *
 * <p>The {@code findByTitleContainingIgnoreCase} method retrieves a list of events
 * that have titles containing the specified search term, ignoring case.
 *
 * <p>The {@code findByDescriptionContainingIgnoreCase} method retrieves a list of events
 * that have descriptions containing the specified search term, ignoring case.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, String> {

	/**
	 * Retrieves a list of events that have titles containing the specified search term,
	 * ignoring case.
	 *
	 * @param title the search term to match against event titles
	 * @return a list of events that have titles containing the search term, ignoring case
	 */
	List<Event> findByTitleContainingIgnoreCase(String title);

	/**
	 * Retrieves a list of events that have descriptions containing the specified search term,
	 * ignoring case.
	 *
	 * @param description the search term to match against event descriptions
	 * @return a list of events that have descriptions containing the search term, ignoring case
	 */
	List<Event> findByDescriptionContainingIgnoreCase(String description);
}
