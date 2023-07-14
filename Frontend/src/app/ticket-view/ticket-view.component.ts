import { Component, Inject, ViewChild, ElementRef } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { jsPDF } from "jspdf";

@Component({
  selector: "app-ticket-view",
  templateUrl: "./ticket-view.component.html",
  styleUrls: ["./ticket-view.component.css"],
})
export class TicketViewComponent {
  @ViewChild("ticketView", { static: false }) ticketViewRef!: ElementRef; // Reference to the ticket view element in the template
  @ViewChild("ticketFooter", { static: false }) ticketFooterRef!: ElementRef; // Reference to the ticket footer element in the template

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<TicketViewComponent>
  ) {}

  onDownloadClick(): void {
    const doc = new jsPDF("p", "mm", "a4"); // Create a new jsPDF instance
    const content = this.ticketViewRef.nativeElement; // Get the native element of the ticket view
    this.ticketFooterRef.nativeElement.remove(); // Remove the ticket footer from the DOM

    const margins = {
      top: 20,
      left: 20,
      right: 20,
      bottom: 20,
    };

    const contentWidth =
      doc.internal.pageSize.getWidth() - margins.left - margins.right; // Calculate the content width
    const contentHeight =
      doc.internal.pageSize.getHeight() - margins.top - margins.bottom; // Calculate the content height

    doc.html(content, {
      x: margins.left,
      y: margins.top,
      callback: () => {
        doc.save("ticket.pdf"); // Save the PDF as "ticket.pdf"
      },
      html2canvas: {
        scale: 0.35,
        width: contentWidth,
        height: contentHeight,
      },
    });

    this.dialogRef.close(); // Close the dialog
  }
}
