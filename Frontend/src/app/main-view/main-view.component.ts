import { Component, OnInit } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { MatDialog } from "@angular/material/dialog";
import { MatSnackBar } from "@angular/material/snack-bar";
import { environment } from "src/environments/environment";
import { EmailInputDialogComponent } from "../email-input-dialog/email-input-dialog.component";
import { CreateUserComponent } from "../create-user/create-user.component";
import { TicketViewComponent } from "../ticket-view/ticket-view.component";

@Component({
  selector: "app-main-view",
  templateUrl: "./main-view.component.html",
  styleUrls: ["./main-view.component.css"],
})
export class MainViewComponent implements OnInit {
  events: any[] = []; // Array to store transformed event data

  constructor(
    private http: HttpClient, // HTTP client for making requests
    private dialog: MatDialog, // Material dialog for user interactions
    private snackBar: MatSnackBar // Material snack bar for notifications
  ) {}

  ngOnInit() {
    // Fetch events from the backend API
    this.http
      .get<any[]>(environment.backendUrl + "/api/ticket/get/all")
      .subscribe((response) => {
        // Transform the API response into a structured events array
        this.events = this.transformEvents(response);
      });
  }

  // Function to transform API response into structured events array
  transformEvents(response: any[]): any[] {
    const events: any[] = [];

    // Loop through the API response to transform ticket data into events
    response.forEach((ticket) => {
      // Check if the ticket quantity is greater than 0
      if (ticket.quantity > 0) {
        const eventIndex = events.findIndex((e) => e.id === ticket.event.id);

        // Check if the event already exists in the events array
        if (eventIndex === -1) {
          // Create a new event and add it to the events array
          events.push({
            id: ticket.event.id,
            title: ticket.event.title,
            date: new Date(ticket.event.date),
            description: ticket.event.description,
            location: ticket.event.location,
            ageRestriction: ticket.event.ageRestriction,
            tickets: [
              {
                id: ticket.id,
                type: ticket.type,
                price: ticket.price,
                quantity: ticket.quantity,
              },
            ],
          });
        } else {
          // Add the ticket to the existing event
          events[eventIndex].tickets.push({
            id: ticket.id,
            type: ticket.type,
            price: ticket.price,
            quantity: ticket.quantity,
          });
        }
      }
    });

    // Sort tickets within each event by type
    events.forEach((event) => {
      event.tickets.sort((a: { type: any }, b: { type: any }) =>
        a.type.localeCompare(b.type)
      );
    });

    // Sort events by title
    events.sort((a, b) => a.title.localeCompare(b.title));

    return events; // Return the transformed events array
  }

  // Function to open email input dialog for ticket purchase
  openDialog(ticketId: string): void {
    // Open the email input dialog for purchasing a ticket
    const dialogRef = this.dialog.open(EmailInputDialogComponent, {
      width: "300px",
      disableClose: true,
    });

    // Subscribe to dialog close event
    dialogRef.afterClosed().subscribe((result: string) => {
      if (result) {
        // If an email is provided, search for the user and proceed with the ticket purchase
        this.searchUserByEmail(result, ticketId);
      } else {
        // Handle dialog closed without result (e.g., user canceled)
      }
    });
  }

  // Function to search for a user by email and proceed with ticket purchase
  searchUserByEmail(email: string, ticketId: string): void {
    // Search for a user by email in the backend API
    const searchUrl = environment.backendUrl + "/api/customer/find/email";
    const searchData = { email: email };

    // Perform the search and handle the response
    this.http.post<any>(searchUrl, searchData).subscribe(
      (response) => {
        // If user is found, proceed with the ticket purchase
        this.purchaseTicket(ticketId, response.id);
      },
      (error) => {
        // Handle the case when email is not found
        if (error.status == 404) {
          this.snackBar.open("Email not found...", "OK");
        }
      }
    );
  }

  // Function to purchase a ticket using the backend API
  purchaseTicket(ticketId: string, customerId: string): void {
    // Create purchase data
    const purchaseData = {
      customer: {
        id: customerId,
      },
      ticket: {
        id: ticketId,
      },
    };

    // Perform the ticket purchase and handle the response
    this.http
      .post<any>(environment.backendUrl + "/api/purchase/create", purchaseData)
      .subscribe((response) => {
        // Generate the ticket with a QR code
        this.generateTicketWithQRCode(response);
      });
  }

  // Function to generate a ticket with a QR code
  generateTicketWithQRCode(ticketData: any) {
    // Configure the dialog for displaying the ticket with QR code
    const dialogConfig = {
      width: "500px",
      disableClose: true,
      data: ticketData,
    };

    // Open the ticket view dialog
    this.dialog.open(TicketViewComponent, dialogConfig);
  }

  // Function to open the create user dialog
  openCreateUserDialog(): void {
    // Configure the dialog for creating a new user
    const dialogRef = this.dialog.open(CreateUserComponent, {
      width: "500px",
    });

    // Subscribe to dialog close event
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.snackBar.open("User created", "OK");
      }
    });
  }
}
