package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.InvoiceStatus;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Invoice;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.InvoiceResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.InvoiceRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
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

    public static final String RANDOM_STRING = "0123456789";

    // Method to retrieve all invoices
    public List<InvoiceResponse> getListInvoices() {
        List<Invoice> invoiceList = invoiceRepository.findAll();
        if (invoiceList.isEmpty()) {
            throw new AppException(ErrorCode.INVOICE_NOT_FOUND);
        }
        return covertToListInvoiceResponse(invoiceList);
    }

    public InvoiceResponse getInvoiceById(Integer id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));
        return covertToInvoiceResponse(invoice);
    }

    public List<InvoiceResponse> getInvoiceByUserId() {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        List<Invoice> invoiceList = invoiceRepository.findByUsers(users);
        return covertToListInvoiceResponse(invoiceList);
    }

    public InvoiceResponse createInvoice(Integer orderId) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(orderId, OrderStatusEnum.PENDING)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Invoice invoiceCheck = invoiceRepository.findByOrdersAndStatus(orders, InvoiceStatus.UNPAID);
        if (invoiceCheck != null) {
            invoiceCheck.setStatus(InvoiceStatus.NOT_AVAILABLE);
        }
        Invoice invoice = Invoice.builder()
                .invoiceNo(generateInvoiceNo())
                .orders(orders)
                .users(users)
                .addressCustomer(orders.getDestination())
                .addressStore(orders.getDeparture())
                .vat(orders.getVat())
                .vatAmount(orders.getVatAmount())
                .amount(orders.getAmount())
                .totalAmount(orders.getTotalAmount())
                .status(InvoiceStatus.UNPAID)
                .build();
        invoice = invoiceRepository.save(invoice);
        return covertToInvoiceResponse(invoice);
    }

    public InvoiceResponse removeInvoice(Integer id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));
        invoice.setStatus(InvoiceStatus.NOT_AVAILABLE);
        invoice = invoiceRepository.save(invoice);
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
                .orderId(invoice.getOrders().getId())
                .userId(invoice.getUsers().getId())
                .addressCustomer(invoice.getAddressCustomer())
                .vat(invoice.getVat())
                .totalAmount(invoice.getTotalAmount())
                .addressStore(invoice.getAddressStore())
                .amount(invoice.getAmount())
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

