import { Component, Inject } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: "app-email-input-dialog",
  templateUrl: "./email-input-dialog.component.html",
  styleUrls: ["./email-input-dialog.component.css"],
})
export class EmailInputDialogComponent {
  email: string = ""; // Variable to store the entered email address

  constructor(
    public dialogRef: MatDialogRef<EmailInputDialogComponent>, // Dialog reference for closing the dialog
    @Inject(MAT_DIALOG_DATA) public data: any // Data passed to the dialog
  ) {}

  submitEmail(): void {
    // Check if an email address has been entered
    if (this.email.trim() !== "") {
      this.dialogRef.close(this.email); // Close the dialog and pass the email address
    }
  }
}
