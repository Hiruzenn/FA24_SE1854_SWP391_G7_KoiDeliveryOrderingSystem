package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Invoice;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.invoice.CreateInvoiceRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.InvoiceRespone;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.FishProfileRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.InvoiceRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private FishProfileRepository fishProfileRepository;


    // Method to retrieve all invoices
    public List<InvoiceRespone> getListInvoices() {
        List<Invoice> invoiceList = invoiceRepository.findAll();

        // Check if the list is empty and throw an exception if no invoices are found
        if (invoiceList.isEmpty()) {
            throw new AppException(ErrorCode.INVOICE_NOT_FOUND);
        }

        // Map the list of Invoice entities to InvoiceRespones
        return covertToListInvoiceRespone(invoiceList);
    }

    // Method to retrieve an invoice by ID
    public InvoiceRespone getInvoiceById(int id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        // Map the Invoice entity to InvoiceRespone
        return covertToInvoiceRespone(invoice);
    }

    // Method to create a new invoice
    public InvoiceRespone createInvoice(
            CreateInvoiceRequest createInvoiceRequest,
            int orderId) {
        Users users = accountUtils.getCurrentUser();
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if (users == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        Invoice invoice = Invoice.builder()
                .invoiceNo(createInvoiceRequest.getInvoiceNo()) // Add invoice number
                .staff(createInvoiceRequest.getStaff()) // Add staff
                .orders(orders) // Link the existing order
                .users(users) // Link the current user
                .healCareDeliveryHistories(createInvoiceRequest.getHealCareDeliveryHistories()) // Add healthcare delivery history
                .date(createInvoiceRequest.getDate()) // Add date
                .addressStore(createInvoiceRequest.getAddressStore()) // Add store address
                .addressCustomer(createInvoiceRequest.getAddressCustomer()) // Add customer address
                .vat(createInvoiceRequest.getVat()) // Add VAT
                .amount(createInvoiceRequest.getAmount()) // Add amount
                .totalAmount(createInvoiceRequest.getTotalAmount()) // Add total amount
                .build();


        invoice = invoiceRepository.save(invoice);


        return covertToInvoiceRespone(invoice);
    }

    // Method to update an existing invoice by ID
    public InvoiceRespone updateInvoice(Integer id, CreateInvoiceRequest createInvoiceRequest) {
        Users users = accountUtils.getCurrentUser();

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        invoice.setInvoiceNo(createInvoiceRequest.getInvoiceNo());
        invoice.setStaff(createInvoiceRequest.getStaff());
        invoice.setHealCareDeliveryHistories(createInvoiceRequest.getHealCareDeliveryHistories());
        invoice.setDate(createInvoiceRequest.getDate());
        invoice.setAddressStore(createInvoiceRequest.getAddressStore());
        invoice.setAddressCustomer(createInvoiceRequest.getAddressCustomer());
        invoice.setVat(createInvoiceRequest.getVat());
        invoice.setAmount(createInvoiceRequest.getAmount());
        invoice.setTotalAmount(createInvoiceRequest.getTotalAmount());
        invoice = invoiceRepository.save(invoice);
        return covertToInvoiceRespone(invoice);
    }

    // Method to delete an invoice by ID

    public InvoiceRespone removeInvoice(Integer id) {
        Users users = accountUtils.getCurrentUser();

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        invoice.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        invoice = invoiceRepository.save(invoice);
        return covertToInvoiceRespone(invoice);
    }
    public List<InvoiceRespone> covertToListInvoiceRespone(List<Invoice> invoiceList)
    {
        List<InvoiceRespone> invoiceResponeList= new ArrayList<>();
        for (Invoice invoices : invoiceList)
        {
            invoiceResponeList.add(covertToInvoiceRespone(invoices));
        }
        return invoiceResponeList;
    }

    public InvoiceRespone covertToInvoiceRespone(Invoice invoice){
        return InvoiceRespone.builder()
                .id(invoice.getId())
                .invoiceNo(invoice.getInvoiceNo())
                .staff(invoice.getStaff())
                .orders(invoice.getOrders())
                .users(invoice.getUsers())
                .vat(invoice.getVat())
                .addressCustomer(invoice.getAddressCustomer())
                .totalAmount(invoice.getTotalAmount())
                .addressStore(invoice.getAddressStore())
                .date(invoice.getDate())
                .amount(invoice.getAmount())
                .healCareDeliveryHistories(invoice.getHealCareDeliveryHistories())
                .createBy(invoice.getCreateBy())
                .createAt(invoice.getCreateAt())
                .updateBy(invoice.getUpdateBy())
                .updateAt(invoice.getUpdateAt())
                .status(invoice.getStatus())
                .build();
    }
    
    
}

