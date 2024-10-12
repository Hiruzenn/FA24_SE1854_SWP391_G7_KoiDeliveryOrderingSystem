package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.InvoiceDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.invoice.CreateInvoiceRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // Endpoint to retrieve the list of all invoices
    @GetMapping
    public ResponseEntity<ApiResponse<List<InvoiceDTO>>> getAllInvoices() {
        List<InvoiceDTO> invoices = invoiceService.getListInvoices();

        // Build and return the ApiResponse
        ApiResponse<List<InvoiceDTO>> response = ApiResponse.<List<InvoiceDTO>>builder()
                .code(200) // HTTP OK
                .message("Invoices retrieved successfully")
                .result(invoices)
                .build();

        return ResponseEntity.ok(response); // Return HTTP 200 OK
    }

    // Endpoint to retrieve an invoice by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceDTO>> getInvoiceById(@PathVariable int id) {
        InvoiceDTO invoiceDTO = invoiceService.getInvoiceById(id);

        // Build and return the ApiResponse if the invoice is found
        ApiResponse<InvoiceDTO> response = ApiResponse.<InvoiceDTO>builder()
                .code(200) // HTTP OK
                .message("Invoice retrieved successfully")
                .result(invoiceDTO)
                .build();

        return ResponseEntity.ok(response); // Return HTTP 200 OK
    }

    // Endpoint to create a new invoice
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<InvoiceDTO>> createInvoice(@RequestBody CreateInvoiceRequest createInvoiceRequest) {
        try {
            // Call the service to create the invoice
            ApiResponse<InvoiceDTO> createdInvoiceResponse = invoiceService.createInvoice(createInvoiceRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInvoiceResponse); // Return HTTP 201 Created
        } catch (AppException e) {
            // Handle any application exceptions
            ApiResponse<InvoiceDTO> response = ApiResponse.<InvoiceDTO>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return HTTP 400 Bad Request
        } catch (Exception e) {
            // Handle unexpected errors
            ApiResponse<InvoiceDTO> response = ApiResponse.<InvoiceDTO>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return HTTP 500 Internal Server Error
        }
    }

    // Endpoint to update an existing invoice by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<InvoiceDTO>> updateInvoice(@PathVariable Integer id, @RequestBody CreateInvoiceRequest createInvoiceRequest) {
        try {
            // Call the service to update the invoice
            ApiResponse<InvoiceDTO> updatedInvoiceResponse = invoiceService.updateInvoice(id, createInvoiceRequest);
            return ResponseEntity.ok(updatedInvoiceResponse); // Return HTTP 200 OK
        } catch (AppException e) {
            // Handle application-specific exceptions
            ApiResponse<InvoiceDTO> response = ApiResponse.<InvoiceDTO>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return HTTP 400 Bad Request
        } catch (Exception e) {
            // Handle unexpected exceptions
            ApiResponse<InvoiceDTO> response = ApiResponse.<InvoiceDTO>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return HTTP 500 Internal Server Error
        }
    }

    // Endpoint to delete an invoice by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInvoice(@PathVariable Integer id) {
        try {
            // Call the service to delete the invoice
            ApiResponse<Void> deleteResponse = invoiceService.deleteInvoice(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deleteResponse); // Return HTTP 204 No Content
        } catch (AppException e) {
            // Handle specific exceptions like invoice not found
            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return HTTP 400 Bad Request
        } catch (Exception e) {
            // Handle any unexpected errors
            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return HTTP 500 Internal Server Error
        }
    }
}
