import { Component, OnInit } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { MatDialog } from "@angular/material/dialog";
import { EmailInputDialogComponent } from "./email-input-dialog/email-input-dialog.component";
import { MatSnackBar } from "@angular/material/snack-bar";
import { TicketViewComponent } from "./ticket-view/ticket-view.component";
import { environment } from "../environments/environment";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent implements OnInit {
  events: any[] = [];

  constructor(
    private http: HttpClient,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    // Fetch events from the backend API
    this.http
      .get<any[]>(environment.backendUrl + "/api/ticket/get/all")
      .subscribe((response) => {
        this.events = this.transformEvents(response);
      });
  }

  transformEvents(response: any[]): any[] {
    const events: any[] = [];

    // Transform the API response into a structured events array
    response.forEach((ticket) => {
      if (ticket.quantity > 0) {
        const eventIndex = events.findIndex((e) => e.id === ticket.event.id);

        if (eventIndex === -1) {
          // Add a new event to the events array
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

    //Here, the Tickets will be sorted by the Typename. (For better usability)
    events.forEach((event) => {
      event.tickets.sort((a: { type: any }, b: { type: any }) =>
        a.type.localeCompare(b.type)
      );
    });

    return events;
  }

  openDialog(ticketId: string): void {
    // Open the email input dialog for purchasing a ticket
    const dialogRef = this.dialog.open(EmailInputDialogComponent, {
      width: "300px",
      disableClose: true,
    });

    dialogRef.afterClosed().subscribe((result: string) => {
      if (result) {
        // If an email is provided, search for the user and proceed with the ticket purchase
        this.searchUserByEmail(result, ticketId);
      } else {
        // Handle dialog closed without result (e.g., user canceled)
      }
    });
  }

  searchUserByEmail(email: string, ticketId: string): void {
    // Search for a user by email in the backend API
    const searchUrl = environment.backendUrl + "/api/customer/find/email";
    const searchData = { email: email };

    this.http.post<any>(searchUrl, searchData).subscribe(
      (response) => {
        // If user is found, proceed with the ticket purchase
        this.purchaseTicket(ticketId, response.id);
      },
      (error) => {
        if (error.status == 404) {
          // Handle case when email is not found
          this.snackBar.open("Email not found...", "OK");
        }
      }
    );
  }

  purchaseTicket(ticketId: string, customerId: string): void {
    // Purchase the ticket using the backend API
    const purchaseData = {
      customer: {
        id: customerId,
      },
      ticket: {
        id: ticketId,
      },
    };

    this.http
      .post<any>(environment.backendUrl + "/api/purchase/create", purchaseData)
      .subscribe((response) => {
        // Generate the ticket with a QR code
        this.generateTicketWithQRCode(response);
      });
  }

  generateTicketWithQRCode(ticketData: any) {
    // Open the ticket view dialog to display the ticket with a QR code
    const dialogConfig = {
      width: "500px",
      disableClose: true,
      data: ticketData,
    };

    this.dialog.open(TicketViewComponent, dialogConfig);
  }
}
