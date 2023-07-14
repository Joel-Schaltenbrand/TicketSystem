# Event Ticket System

This repository contains the backend and app components of an Event Ticket System.

## License

This project is licensed under the [MIT License](LICENSE.md).

## Backend

The backend provides a REST API for managing various aspects of the ticket system, including customers, tickets, purchases, events, and more. It is built using Java 19 and relies on the powerful Spring ecosystem for efficient development and deployment.

### Setup and Configuration

To set up the backend, please follow these steps:

1. Start the MySQL server. You don't have to set up a database; it will be created automatically on the first start.
2. Open the `application.properties` file located in the backend project.
3. Update the `secret.key` property with a strong and secure key. This key is used for token generation and validation.
4. Run the backend application using the command: `mvn spring-boot:run`.
5. The backend API documentation can be accessed at `http://localhost:8080/` or your local IP address in your network. It provides detailed information about the available endpoints and their usage.

#### <mark>Write down the IP of the backend computer. You will need it later.</mark>

It can be found in the console _Use this IP if you are using the Mobile Ticket System App: XYZ_

or

1.  Open CMD on the machine running the backend.
2.  Type `ipconfig`.
3.  Find the IPv4 Address

### Dependencies

The backend project relies on the following dependencies:

- [springdoc](https://springdoc.org/): Used for automatic generation of API documentation.
- [projectlombok](https://projectlombok.org/): Provides boilerplate code reduction and simplifies the development process.
- [mysql-connector-java](https://mvnrepository.com/artifact/mysql/mysql-connector-java): Enables connectivity with MySQL database.
- [spring-boot-starter-web](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html): Servlet Web, Reactive Web, Embedded Container Support, Graceful Shutdown, and more.
- [spring-boot-starter-data-jpa](https://spring.io/projects/spring-data-jpa): Simplifies data access and persistence with JPA.

## Frontend

The frontend provides an interface to browse events, purchase tickets, and view/download their tickets. It is built using Angular and TypeScript. (Please note that the frontend does not include user management and the example provided does not have any security implications. Its only an example.)

### Setup and Configuration

To set up the frontend, please follow these steps:

1. Install Node.js and npm on your machine.
2. Navigate to the frontend directory: `cd frontend`
3. Install the dependencies by running: `npm install`
4. Open the `src/environments/environment.ts` file and update the `backendUrl` variable.
5. Start the frontend application by running: `ng serve`
6. Access the frontend application at `http://localhost:4200` in your web browser.

### Dependencies

The frontend project relies on the following dependencies:

- [rxjs](https://rxjs.dev/): A library for reactive programming using Observables.
- [jspdf](https://github.com/MrRio/jsPDF): A library for generating PDFs in JavaScript.
- [zone.js](https://zone.js.org/): A library for detecting and managing asynchronous operations in JavaScript.
- [@angular/http](https://angular.io/guide/http): Angular's HTTP module for making HTTP requests.
- [@angular/core](https://angular.io/docs): The core framework for building Angular applications.
- [angularx-qrcode](https://www.npmjs.com/package/angularx-qrcode): A library for generating QR codes in Angular.
- [@angular/forms](https://angular.io/guide/forms): Angular's form module for handling form validation and submission.
- [@angular/router](https://angular.io/guide/router): The Angular router for managing navigation between different views.
- [@angular/material](https://material.angular.io/): Material Design components for Angular applications.
- [@angular/common](https://angular.io/docs): Common utilities for Angular applications.

## App

The app component provides a user-friendly interface for scanning and validating event tickets using the device's camera. It is developed in Java 19 using Android Studio and Gradle build system.

### Setup and Configuration

To set up the app, please follow these steps:

1. Open the app project in Android Studio.
2. Build the app in Android Studio and install it on an Android device using ADB, either directly from Android Studio or through any preferred method.
3. Set the backend URL in the settings (InApp)

### Dependencies

The app relies on the following dependencies:

- [com.google.code.gson:gson](https://github.com/google/gson): Used for JSON serialization and deserialization.
- [com.squareup.okhttp3:okhttp](https://square.github.io/okhttp/): A powerful HTTP client for making API requests.
- [androidx.preference:preference](https://developer.android.com/jetpack/androidx/releases/preference): Build interactive settings screens without needing to interact with device storage or manage the UI.
- [androidx.appcompat:appcompat](https://developer.android.com/jetpack/androidx/releases/appcompat): Allows access to new APIs on older API versions of the platform.
- [com.android.tools:desugar_jdk_libs](https://developer.android.com/studio/write/java8-support): Enables Java 8 features for older Android versions.
- [com.google.android.material:material](https://m3.material.io/get-started): Provides a set of Material Design components for the app's UI.
- [androidx.constraintlayout:constraintlayout](https://developer.android.com/jetpack/androidx/releases/constraintlayout): Position and size widgets in a flexible way with relative positioning.
- [com.google.android.gms:play-services-code-scanner](https://developers.google.com/ml-kit/vision/barcode-scanning/code-scanner): A library for scanning and decoding QR codes.
