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

package ch.schaltenbrandlabs.ticketsystem;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Gson TypeAdapter for serializing and deserializing LocalDateTime objects.
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	/**
	 * Writes a LocalDateTime object to JSON.
	 *
	 * @param out   The JsonWriter object.
	 * @param value The LocalDateTime object to write.
	 * @throws IOException If an I/O error occurs during writing.
	 */
	@Override
	public void write(JsonWriter out, LocalDateTime value) throws IOException {
		out.value(FORMATTER.format(value));
	}

	/**
	 * Reads a LocalDateTime object from JSON.
	 *
	 * @param in The JsonReader object.
	 * @return The deserialized LocalDateTime object.
	 * @throws IOException If an I/O error occurs during reading.
	 */
	@Override
	public LocalDateTime read(JsonReader in) throws IOException {
		String dateString = in.nextString();
		return LocalDateTime.parse(dateString, FORMATTER);
	}
}
