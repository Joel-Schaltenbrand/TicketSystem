import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { HttpClientModule } from "@angular/common/http";
import { MatDialogModule } from "@angular/material/dialog";
import { EmailInputDialogComponent } from "./email-input-dialog/email-input-dialog.component";
import { FormsModule } from "@angular/forms";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { TicketViewComponent } from "./ticket-view/ticket-view.component";
import { QRCodeModule } from "angularx-qrcode";
import { MatIconModule } from "@angular/material/icon";
import { CreateUserComponent } from "./create-user/create-user.component";
import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MainViewComponent } from "./main-view/main-view.component";

@NgModule({
  declarations: [
    AppComponent, // Main app component
    EmailInputDialogComponent, // Custom dialog for email input
    TicketViewComponent, // Component for displaying tickets
    CreateUserComponent, // Component for creating users
    MainViewComponent, // Main view component
  ],
  imports: [
    BrowserModule, // Required for running the application in the browser
    AppRoutingModule, // Routing configuration module
    BrowserAnimationsModule, // Required for animations
    HttpClientModule, // Required for making HTTP requests
    MatDialogModule, // Material dialog module
    FormsModule, // Required for forms and ngModel directive
    MatSnackBarModule, // Material snack bar module
    QRCodeModule, // QR code module for generating QR codes
    MatIconModule, // Material icon module
    MatInputModule, // Material input module
    MatButtonModule, // Material button module
    MatFormFieldModule, // Material form field module
  ],
  providers: [], // Service providers (if any) can be added here
  bootstrap: [AppComponent], // The root component to bootstrap the application
})
export class AppModule {}
