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

import ch.schaltenbrandlabs.ticketsystem.model.Event;
import ch.schaltenbrandlabs.ticketsystem.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * The {@code EventController} class handles HTTP requests related to the {@code Event} entity.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/event")
public class EventController extends AbstractBaseController<Event> {

	private final EventService eventService;

	/**
	 * Constructs a new {@code EventController} with the provided {@code EventService}.
	 *
	 * @param eventService the service to be used for performing event operations
	 */
	public EventController(EventService eventService) {
		super(eventService);
		this.eventService = eventService;
	}

	/**
	 * Searches events by title.
	 *
	 * @param request the request body containing the keyword parameter
	 * @return a {@code ResponseEntity} containing a list of events with {@code HttpStatus.OK}
	 */
	@PostMapping(value = "/find/title", produces = {"application/json"}, consumes = {"application/json"})
	public ResponseEntity<List<Event>> searchByTitle(@RequestBody Map<String, String> request) {
		return new ResponseEntity<>(eventService.searchByTitle(request.get("keyword")), HttpStatus.OK);
	}

	/**
	 * Searches events by description.
	 *
	 * @param request the request body containing the keyword parameter
	 * @return a {@code ResponseEntity} containing a list of events with {@code HttpStatus.OK}
	 */
	@PostMapping(value = "/find/description", produces = {"application/json"}, consumes = {"application/json"})
	public ResponseEntity<List<Event>> searchByDescription(@RequestBody Map<String, String> request) {
		return new ResponseEntity<>(eventService.searchByDescription(request.get("keyword")), HttpStatus.OK);
	}
}
