package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Invoice;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.invoice.CreateInvoiceRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.invoice.UpdateInvoiceRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.InvoiceResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.InvoiceRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.UserRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
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
    private UserRepository userRepository;

    public static final String RANDOM_STRING = "0123456789";

    // Method to retrieve all invoices
    public List<InvoiceResponse> getListInvoices() {
        List<Invoice> invoiceList = invoiceRepository.findByStatus(SystemStatusEnum.AVAILABLE);
        if (invoiceList.isEmpty()) {
            throw new AppException(ErrorCode.INVOICE_NOT_FOUND);
        }
        return covertToListInvoiceResponse(invoiceList);
    }

    // Method to retrieve an invoice by ID
    public InvoiceResponse getInvoiceById(Integer id) {
        Invoice invoice = invoiceRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        return covertToInvoiceResponse(invoice);
    }

    // Method to create a new invoice
    public InvoiceResponse createInvoice(CreateInvoiceRequest request) {
        Users users = accountUtils.getCurrentUser();
        Orders orders = orderRepository.findByIdAndStatus(request.getOrderId(), SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Users customer = userRepository.findById(users.getId())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Invoice invoice = Invoice.builder()
                .invoiceNo(generateInvoiceNo())
                .staff(request.getStaff())
                .orders(orders)
                .users(customer)
                .date(request.getDate())
                .addressStore(request.getAddressStore())
                .addressCustomer(request.getAddressCustomer())
                .vat(request.getVat())
                .amount(request.getAmount())
                .totalAmount(request.getTotalAmount())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        invoice = invoiceRepository.save(invoice);
        return covertToInvoiceResponse(invoice);
    }

    // Method to update an existing invoice by ID
    public InvoiceResponse updateInvoice(Integer id, UpdateInvoiceRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Invoice invoice = invoiceRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));

        invoice.setDate(request.getDate());
        invoice.setAddressStore(request.getAddressStore());
        invoice.setAddressCustomer(request.getAddressCustomer());
        invoice.setVat(request.getVat());
        invoice.setAmount(request.getAmount());
        invoice.setTotalAmount(request.getTotalAmount());
        invoice.setUpdateAt(LocalDateTime.now());
        invoice.setUpdateBy(users.getId());
        invoice = invoiceRepository.save(invoice);
        return covertToInvoiceResponse(invoice);
    }


    public InvoiceResponse removeInvoice(Integer id) {
        Users users = accountUtils.getCurrentUser();
        Invoice invoice = invoiceRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));
        invoice.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        invoice = invoiceRepository.save(invoice);
        invoice.setUpdateAt(LocalDateTime.now());
        invoice.setUpdateBy(users.getId());
        return covertToInvoiceResponse(invoice);
    }

    public List<InvoiceResponse> covertToListInvoiceResponse(List<Invoice> invoiceList) {
        List<InvoiceResponse> invoiceResponseList = new ArrayList<>();
        for (Invoice invoices : invoiceList) {
            invoiceResponseList.add(covertToInvoiceResponse(invoices));
        }
        return invoiceResponseList;
    }

    public InvoiceResponse covertToInvoiceResponse(Invoice invoice) {
        return InvoiceResponse.builder()
                .id(invoice.getId())
                .invoiceNo(invoice.getInvoiceNo())
                .staff(invoice.getStaff())
                .orderId(invoice.getOrders().getId())
                .userId(invoice.getUsers().getId())
                .vat(invoice.getVat())
                .addressCustomer(invoice.getAddressCustomer())
                .totalAmount(invoice.getTotalAmount())
                .addressStore(invoice.getAddressStore())
                .date(invoice.getDate())
                .amount(invoice.getAmount())
                .createAt(invoice.getCreateAt())
                .createBy(invoice.getCreateBy())
                .updateAt(invoice.getUpdateAt())
                .updateBy(invoice.getUpdateBy())
                .status(invoice.getStatus())
                .build();
    }

    private String generateInvoiceNo() {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder;
        String invoiceNo = "";
        do {
            stringBuilder = new StringBuilder("INV");
            for (int i = 0; i < 5; i++) {
                int randomIndex = random.nextInt(5);
                stringBuilder.append(RANDOM_STRING.charAt(randomIndex));
            }
            invoiceNo = stringBuilder.toString();
        } while (orderRepository.existsByOrderCode(invoiceNo));
        return invoiceNo;
    }
}

