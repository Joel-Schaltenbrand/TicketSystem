import { Component } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: "app-create-user",
  templateUrl: "./create-user.component.html",
  styleUrls: ["./create-user.component.css"],
})
export class CreateUserComponent {
  user = {
    firstName: "",
    lastName: "",
    street: "",
    zip: "",
    location: "",
    email: "",
  };

  constructor(
    public dialogRef: MatDialogRef<CreateUserComponent>, // Reference to the dialog
    private http: HttpClient // HTTP client for making requests
  ) {}

  // Function to create a new user
  createUser() {
    const url = environment.backendUrl + "/api/customer/create"; // Backend API URL for creating a user
    this.http.post(url, this.user).subscribe((response) => {
      // Close the dialog and signal success
      this.dialogRef.close(true);
    });
  }
}
