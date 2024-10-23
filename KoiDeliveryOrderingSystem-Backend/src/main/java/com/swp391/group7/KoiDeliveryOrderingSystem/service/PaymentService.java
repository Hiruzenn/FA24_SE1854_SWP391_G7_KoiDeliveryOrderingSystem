package com.swp391.group7.KoiDeliveryOrderingSystem.service;


import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Payment;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.payment.CreatePaymentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.PaymentResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;


import com.swp391.group7.KoiDeliveryOrderingSystem.repository.PaymentRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountUtils accountUtils;

    public static final String RANDOM_STRING = "0123456789";

    public PaymentResponse createPayment(Integer orderId, CreatePaymentRequest createPaymentRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Payment payment = Payment.builder()
                .paymentCode(generatePaymentCode())
                .users(users)
                .orders(orders)
                .paymentMethod(createPaymentRequest.getPaymentMethod())
                .amount(createPaymentRequest.getAmount())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        paymentRepository.save(payment);
        return convertToPaymentResponse(payment);
    }

    public List<PaymentResponse> viewPaymentsByOrderId(Integer orderId) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        List<Payment> paymentList = paymentRepository.findByOrders(orders);
        return convertToListPaymentResponse(paymentList);
    }

    public List<PaymentResponse> viewPaymentsByCustomer() {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        List<Payment> paymentList = paymentRepository.findByUsers(users);
        return convertToListPaymentResponse(paymentList);
    }

    public List<PaymentResponse> viewAllPayment(){
        List<Payment> paymentList = paymentRepository.findAll();
        return convertToListPaymentResponse(paymentList);
    }


    private List<PaymentResponse> convertToListPaymentResponse(List<Payment> paymentList) {
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for (Payment payment : paymentList) {
            paymentResponses.add(convertToPaymentResponse(payment));
        }
        return paymentResponses;
    }

    private PaymentResponse convertToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .paymentCode(payment.getPaymentCode())
                .orderId(payment.getOrders().getId())
                .customerId(payment.getUsers().getId())
                .paymentMethod(payment.getPaymentMethod())
                .amount(payment.getAmount())
                .createAt(payment.getCreateAt())
                .createBy(payment.getCreateBy())
                .updateAt(payment.getUpdateAt())
                .build();
    }
    private String generatePaymentCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder;
        String paymentCode = "";
        do {
            stringBuilder = new StringBuilder("PAY");
            for (int i = 0; i < 5; i++) {
                int randomIndex = random.nextInt(5);
                stringBuilder.append(RANDOM_STRING.charAt(randomIndex));
            }
            paymentCode = stringBuilder.toString();
        } while (orderRepository.existsByOrderCode(paymentCode));
        return paymentCode;
    }
}
