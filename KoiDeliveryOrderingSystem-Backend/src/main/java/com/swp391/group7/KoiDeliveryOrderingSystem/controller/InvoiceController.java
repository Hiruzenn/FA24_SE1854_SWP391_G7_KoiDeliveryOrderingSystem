package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

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

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getAllInvoices() {
        var invoices = invoiceService.getListInvoices();
        return ResponseEntity.ok(ApiResponse.<List<InvoiceResponse>>builder()
                .code(200)
                .message("Invoices retrieved successfully")
                .result(invoices)
                .build());
    }

    @GetMapping("view-one/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getInvoiceById(@PathVariable int id) {
        var InvoiceResponse = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(ApiResponse.<InvoiceResponse>builder()
                .code(200)
                .message("Invoice retrieved successfully")
                .result(InvoiceResponse)
                .build());
    }

    @GetMapping("view-by-customer")
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getInvoiceByUserId() {
        var result = invoiceService.getInvoiceByUserId();
        return ResponseEntity.ok(ApiResponse.<List<InvoiceResponse>>builder()
                .code(200)
                .message("Invoice by User")
                .result((result))
                .build());

    }

    @PostMapping("/create/{orderId}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> createInvoice(@PathVariable("orderId") Integer orderId) {
        var result = invoiceService.createInvoice(orderId);
        return ResponseEntity.ok(ApiResponse.<InvoiceResponse>builder()
                .code(200)
                .message("Invoice created successfully")
                .result(result)
                .build());
    }

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
