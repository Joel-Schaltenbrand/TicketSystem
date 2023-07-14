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

import ch.schaltenbrandlabs.ticketsystem.model.Event;
import ch.schaltenbrandlabs.ticketsystem.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The {@code EventService} class provides operations for managing events.
 */
@Service
public class EventService extends AbstractBaseService<Event> {

	private final EventRepository eventRepository;

	/**
	 * Constructs a new {@code EventService} with the provided {@code EventRepository}.
	 *
	 * @param eventRepository the repository to be used for performing event operations
	 */
	public EventService(EventRepository eventRepository) {
		super(eventRepository);
		this.eventRepository = eventRepository;
	}

	/**
	 * Searches events by title.
	 *
	 * @param keyword the keyword to search for in the event title
	 * @return a list of events matching the title search criteria
	 */
	public List<Event> searchByTitle(String keyword) {
		return eventRepository.findByTitleContainingIgnoreCase(keyword);
	}

	/**
	 * Searches events by description.
	 *
	 * @param keyword the keyword to search for in the event description
	 * @return a list of events matching the description search criteria
	 */
	public List<Event> searchByDescription(String keyword) {
		return eventRepository.findByDescriptionContainingIgnoreCase(keyword);
	}
}
