package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.invoice.CreateInvoiceRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.invoice.UpdateInvoiceRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.InvoiceResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("invoice")

public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getAllInvoices() {
        List<InvoiceResponse> invoices = invoiceService.getListInvoices();
        return ResponseEntity.ok(ApiResponse.<List<InvoiceResponse>>builder()
                .code(200) // HTTP OK
                .message("Invoices retrieved successfully")
                .result(invoices)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getInvoiceById(@PathVariable int id) {
        InvoiceResponse InvoiceResponse = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(ApiResponse.<InvoiceResponse>builder()
                .code(200) // HTTP OK
                .message("Invoice retrieved successfully")
                .result(InvoiceResponse)
                .build());
    }

    // Endpoint to create a new invoice
    @PostMapping("/create/") // Ensure the orderId is part of the path variable
    public ResponseEntity<ApiResponse<InvoiceResponse>> createInvoice(@RequestBody CreateInvoiceRequest createInvoiceRequest) {
        var result = invoiceService.createInvoice(createInvoiceRequest);
        return ResponseEntity.ok(ApiResponse.<InvoiceResponse>builder()
                .code(200)
                .message("Invoice created successfully")
                .result(result)
                .build());
    }


    // Endpoint to update an existing invoice by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> updateInvoice(@PathVariable Integer id, @RequestBody UpdateInvoiceRequest updateInvoiceRequest) {
        var result = invoiceService.updateInvoice(id, updateInvoiceRequest);
        return ResponseEntity.ok(ApiResponse.<InvoiceResponse>builder()
                .code(200)
                .message("Invoice updated successfully")
                .result(result)
                .build());
    }

    // Endpoint to delete an invoice by ID
    @PutMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> deleteInvoice(@PathVariable Integer id) {
        var result = invoiceService.removeInvoice(id);
        return ResponseEntity.ok(ApiResponse.<InvoiceResponse>builder()
                .code(200)
                .message("Invoice removed successfully")
                .result(result)
                .build());
    }
}
