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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import ch.schaltenbrandlabs.ticketsystem.model.Customer;
import ch.schaltenbrandlabs.ticketsystem.model.Result;
import ch.schaltenbrandlabs.ticketsystem.model.Ticket;
import ch.schaltenbrandlabs.ticketsystem.model.Token;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Main activity of the application.
 */
public class MainActivity extends AppCompatActivity {

	private String backendUrl;
	private final Locale locale = new Locale("de", "CH");
	private ProgressBar progressBar;
	private TextView statusText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		backendUrl = getUrlFromPreferences();
		setContentView(R.layout.activity_main);
		progressBar = findViewById(R.id.progressBar);
		statusText = findViewById(R.id.statusText);
		findViewById(R.id.button_scan_next_main).setOnClickListener(view -> startQRCodeScanner());
	}

	@Override
	protected void onResume() {
		super.onResume();
		backendUrl = getUrlFromPreferences();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.settings) {
			startActivity(new Intent(MainActivity.this, SettingsActivity.class));
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Retrieves the backend URL from the app preferences.
	 *
	 * @return The backend URL.
	 */
	private String getUrlFromPreferences() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		return preferences.getString("BACKEND_URL", getString(R.string.defaultUrl));
	}

	/**
	 * Starts the QR code scanner.
	 */
	private void startQRCodeScanner() {
		GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
				.setBarcodeFormats(Barcode.FORMAT_QR_CODE)
				.build();
		GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(this, options);
		scanner.startScan().addOnSuccessListener(barcode -> performVerifyRequest(barcode.getRawValue()));
	}

	/**
	 * Performs the verification request with the provided token.
	 *
	 * @param token The token to verify.
	 */
	private void performVerifyRequest(String token) {
		progressBar.setVisibility(View.VISIBLE);
		statusText.setVisibility(View.GONE);
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		JSONObject jsonRequest = new JSONObject();
		try {
			jsonRequest.put("token", token);
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		RequestBody body = RequestBody.create(jsonRequest.toString(), mediaType);
		Request request = new Request.Builder()
				.url(String.format("%s%s", backendUrl, "/api/purchase/verify"))
				.post(body)
				.addHeader("Content-Type", "application/json")
				.build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(@NonNull Call call, @NonNull IOException e) {
				runOnUiThread(() -> requestFailed());

			}

			@Override
			public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
				assert response.body() != null;
				final String result = response.body().string();
				final int statusCode = response.code();
				new Handler(Looper.getMainLooper()).post(() -> processVerifyResponse(result, statusCode));
			}
		});
	}

	/**
	 * Handles the failure of the verification request.
	 */
	private void requestFailed() {
		progressBar.setVisibility(View.GONE);
		statusText.setVisibility(View.VISIBLE);
		statusText.setText(getText(R.string.request_failed));
	}

	/**
	 * Processes the response of the verification request.
	 *
	 * @param result     The response result.
	 * @param statusCode The status code of the response.
	 */
	private void processVerifyResponse(String result, int statusCode) {
		if (statusCode == 200) {
			progressBar.setVisibility(View.GONE);
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
					.create();
			showTicket(gson.fromJson(result, Result.class));
		} else {
			requestFailed();
		}
	}

	/**
	 * Shows the ticket information in a dialog.
	 *
	 * @param response The result containing the ticket information.
	 */
	private void showTicket(Result response) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.dialog_ticket, null);
		ImageView imageView = layout.findViewById(R.id.image_icon);
		Button next = layout.findViewById(R.id.button_scan_next);
		Button details = layout.findViewById(R.id.button_view_details);
		imageView.setImageDrawable(AppCompatResources.getDrawable(this, response.token().active() ? R.drawable.ic_accepted : R.drawable.ic_rejected));
		alertDialog.setView(layout);
		alertDialog.show();
		next.setOnClickListener(view -> {
			alertDialog.dismiss();
			startQRCodeScanner();
		});
		details.setOnClickListener(view -> {
			alertDialog.dismiss();
			showDetails(response);
		});
		if (response.token().active()) {
			invalidateTicket(response.token().id());
		}
	}

	/**
	 * Invalidates the ticket with the given token ID.
	 *
	 * @param tokenId The ID of the token to invalidate.
	 */
	private void invalidateTicket(String tokenId) {
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		JSONObject jsonRequest = new JSONObject();
		try {
			jsonRequest.put("id", tokenId);
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		RequestBody body = RequestBody.create(jsonRequest.toString(), mediaType);
		Request request = new Request.Builder()
				.url(String.format("%s%s", backendUrl, "/api/token/invalidate"))
				.post(body)
				.addHeader("Content-Type", "application/json")
				.build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				Toast.makeText(MainActivity.this, getText(R.string.invalidate_failed), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) {
				// Empty onResponse implementation
			}
		});
	}

	/**
	 * Shows the detailed information of the ticket in a dialog.
	 *
	 * @param result The result containing the ticket information.
	 */
	private void showDetails(Result result) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.dialog_details, null);
		alertDialog.setView(layout);
		Customer customer = result.customer();
		TextView customerNameTextView = layout.findViewById(R.id.text_customer_name);
		customerNameTextView.setText(String.format(locale, "%s %s", customer.firstName(), customer.lastName()));
		TextView customerAddressTextView = layout.findViewById(R.id.text_customer_address);
		customerAddressTextView.setText(customer.street());
		TextView customerLocationTextView = layout.findViewById(R.id.text_customer_location);
		customerLocationTextView.setText(String.format(locale, "%s %s", customer.zip(), customer.location()));
		TextView customerEmailTextView = layout.findViewById(R.id.text_customer_email);
		customerEmailTextView.setText(customer.email());
		Ticket ticket = result.ticket();
		TextView eventTitleTextView = layout.findViewById(R.id.text_event_title);
		eventTitleTextView.setText(String.format(locale, "%s (%d+)", ticket.event().title(), ticket.event().ageRestriction()));
		TextView eventDateTextView = layout.findViewById(R.id.text_event_date);
		eventDateTextView.setText(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM).format(ticket.event().date()));
		TextView eventLocationTextView = layout.findViewById(R.id.text_event_location);
		eventLocationTextView.setText(ticket.event().location());
		TextView eventDescriptionTextView = layout.findViewById(R.id.text_event_description);
		eventDescriptionTextView.setText(ticket.event().description());
		TextView ticketTypePriceTextView = layout.findViewById(R.id.text_ticket_type_price);
		ticketTypePriceTextView.setText(String.format(locale, "%s-Ticket - %s", ticket.type(),
				NumberFormat.getCurrencyInstance(locale).format(ticket.price())));
		Token token = result.token();
		ImageView imageView = layout.findViewById(R.id.image_icon);
		imageView.setImageDrawable(AppCompatResources.getDrawable(this, token.active() ? R.drawable.ic_accepted : R.drawable.ic_rejected));
		TextView tokenTimestampTextView = layout.findViewById(R.id.text_token_timestamp);
		tokenTimestampTextView.setText(String.format(locale, "Update: %s",
				DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM).format(token.timestamp())));
		layout.findViewById(R.id.button_scan_next).setOnClickListener((view -> {
			alertDialog.dismiss();
			startQRCodeScanner();
		}));
		alertDialog.show();
	}
}
