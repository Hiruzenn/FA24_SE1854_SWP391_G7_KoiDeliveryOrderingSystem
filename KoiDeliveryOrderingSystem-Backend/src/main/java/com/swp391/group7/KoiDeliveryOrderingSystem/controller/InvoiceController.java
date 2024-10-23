package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.invoice.CreateInvoiceRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.InvoiceRespone;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.OrderDetailResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoice")

public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // Endpoint to retrieve the list of all invoices
    @GetMapping
    public ResponseEntity<ApiResponse<List<InvoiceRespone>>> getAllInvoices() {
        List<InvoiceRespone> invoices = invoiceService.getListInvoices();

        // Build and return the ApiResponse
        ApiResponse<List<InvoiceRespone>> response = ApiResponse.<List<InvoiceRespone>>builder()
                .code(200) // HTTP OK
                .message("Invoices retrieved successfully")
                .result(invoices)
                .build();

        return ResponseEntity.ok(response); // Return HTTP 200 OK
    }

    // Endpoint to retrieve an invoice by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceRespone>> getInvoiceById(@PathVariable int id) {
        InvoiceRespone InvoiceRespone = invoiceService.getInvoiceById(id);

        // Build and return the ApiResponse if the invoice is found
        ApiResponse<InvoiceRespone> response = ApiResponse.<InvoiceRespone>builder()
                .code(200) // HTTP OK
                .message("Invoice retrieved successfully")
                .result(InvoiceRespone)
                .build();

        return ResponseEntity.ok(response); // Return HTTP 200 OK
    }

    // Endpoint to create a new invoice
    @PostMapping("/create/{orderId}") // Ensure the orderId is part of the path variable
    public ResponseEntity<ApiResponse<InvoiceRespone>> createInvoice(
            @RequestBody CreateInvoiceRequest createInvoiceRequest,
            @PathVariable int orderId) {

        // Call the service to create the invoice
        var result = invoiceService.createInvoice(createInvoiceRequest, orderId);

        // Build the ApiResponse
        ApiResponse<InvoiceRespone> response = ApiResponse.<InvoiceRespone>builder()
                .code(200)
                .message("Invoice created successfully")
                .result(result)
                .build();

        // Return the response wrapped in ResponseEntity
        return ResponseEntity.ok(response);
    }


    // Endpoint to update an existing invoice by ID
    @PutMapping("/update/{id}")
    public ApiResponse<InvoiceRespone> updateInvoice(@PathVariable Integer id, @RequestBody CreateInvoiceRequest createInvoiceRequest) {
        var result = invoiceService.updateInvoice(id , createInvoiceRequest);
        return  ApiResponse.<InvoiceRespone>builder()
                .code(200)
                .message("Invoice updated successfully")
                .result(result)
                .build();
    }

    // Endpoint to delete an invoice by ID
    @PutMapping("/delete/{id}")
    public ApiResponse<InvoiceRespone> deleteInvoice(@PathVariable Integer id) {
        var result = invoiceService.removeInvoice(id);
        return  ApiResponse.<InvoiceRespone>builder()
                .code(200)
                .message("Invoice removed successfully")
                .result(result)
                .build();
    }
}
