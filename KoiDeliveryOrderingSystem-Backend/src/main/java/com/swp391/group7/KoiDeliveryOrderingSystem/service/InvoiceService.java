package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Invoice;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.InvoiceDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.invoice.CreateInvoiceRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.InvoiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    ModelMapper modelMapper;

    // Method to retrieve all invoices
    public List<InvoiceDTO> getListInvoices() {
        List<Invoice> invoiceList = invoiceRepository.findAll();

        // Check if the list is empty and throw an exception if no invoices are found
        if (invoiceList.isEmpty()) {
            throw new AppException(ErrorCode.INVOICE_NOT_FOUND);
        }

        // Map the list of Invoice entities to InvoiceDTOs
        return invoiceList.stream()
                .map(invoice -> modelMapper.map(invoice, InvoiceDTO.class))
                .toList();
    }

    // Method to retrieve an invoice by ID
    public InvoiceDTO getInvoiceById(int id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        // Map the Invoice entity to InvoiceDTO
        return modelMapper.map(invoice, InvoiceDTO.class);
    }

    // Method to create a new invoice
    public ApiResponse<InvoiceDTO> createInvoice(CreateInvoiceRequest createInvoiceRequest) {
        // Map the CreateInvoiceRequest to the Invoice entity
        Invoice invoice = modelMapper.map(createInvoiceRequest, Invoice.class);

        // Save the invoice to the repository
        invoice = invoiceRepository.save(invoice);

        // Map the saved Invoice entity to InvoiceDTO
        InvoiceDTO createdInvoiceDTO = modelMapper.map(invoice, InvoiceDTO.class);

        // Build and return the ApiResponse with the created invoice
        return ApiResponse.<InvoiceDTO>builder()
                .code(HttpStatus.CREATED.value()) // 201 Created
                .message("Invoice created successfully")
                .result(createdInvoiceDTO)
                .build();
    }

    // Method to update an existing invoice by ID
    public ApiResponse<InvoiceDTO> updateInvoice(Integer id, CreateInvoiceRequest createInvoiceRequest) {
        // Find the invoice by ID, throw exception if not found
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        // Map the update data to the existing invoice entity
        modelMapper.map(createInvoiceRequest, invoice);

        // Save the updated invoice
        invoice = invoiceRepository.save(invoice);

        // Map the updated Invoice entity to InvoiceDTO
        InvoiceDTO updatedInvoiceDTO = modelMapper.map(invoice, InvoiceDTO.class);

        // Build and return the ApiResponse with the updated invoice
        return ApiResponse.<InvoiceDTO>builder()
                .code(HttpStatus.OK.value()) // 200 OK
                .message("Invoice updated successfully")
                .result(updatedInvoiceDTO)
                .build();
    }

    // Method to delete an invoice by ID
    public ApiResponse<Void> deleteInvoice(Integer id) {
        // Check if the invoice exists
        if (!invoiceRepository.existsById(id)) {
            throw new AppException(ErrorCode.INVOICE_NOT_FOUND);
        }

        // Delete the invoice
        invoiceRepository.deleteById(id);

        // Build and return the ApiResponse indicating success
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value()) // 204 No Content
                .message("Invoice deleted successfully")
                .result(null) // No result for deletion
                .build();
    }
}

