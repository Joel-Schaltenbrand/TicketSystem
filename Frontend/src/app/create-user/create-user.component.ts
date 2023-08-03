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
    public dialogRef: MatDialogRef<CreateUserComponent>,
    private http: HttpClient
  ) {}
  createUser() {
    const url = environment.backendUrl + "/api/customer/create";
    this.http.post(url, this.user).subscribe((response) => {
      this.dialogRef.close(true);
    });
  }
}
