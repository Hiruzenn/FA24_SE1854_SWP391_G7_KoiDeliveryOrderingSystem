package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.config.PayOSConfig;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.PaymentStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Payment;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.PaymentResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.PaymentRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.UserRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentData;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PayOSService {
    @Autowired
    private PayOSConfig payOSConfig;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;

    public CheckoutResponseData createPayment(Integer orderId) throws Exception {
        try {
            Random random = new SecureRandom();
            Long orderCode = Math.abs(random.nextLong() % 10_000_000_000L);
            Users users = accountUtils.getCurrentUser();
            Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
            PayOS payOS = new PayOS(payOSConfig.getClientId(), payOSConfig.getApiKey(), payOSConfig.getChecksumKey());
            int totalAmountInt = (int) orders.getTotalAmount();
            PaymentData paymentData = PaymentData.builder()
                    .amount(totalAmountInt)
                    .orderCode(orderCode)
                    .description("Thanh toán đơn hàng")
                    .returnUrl("http://localhost:8080/payos/payment-success?orderId=" + orderId + "&userId=" + users.getId())
                    .cancelUrl("http://localhost:8080/payos/payment-fail")
                    .build();
            CheckoutResponseData result = payOS.createPaymentLink(paymentData);
            return result;
        } catch (
                Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void createPaymentSuccess(Integer orderId, Integer userId) throws Exception {
        Orders orders = orderRepository.findByIdAndStatus(orderId, OrderStatusEnum.AVAILABLE).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Users users = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Payment payment = Payment.builder()
                .users(users)
                .orders(orders)
                .paymentCode(orders.getOrderCode().substring(3))
                .paymentDate(LocalDate.now())
                .paymentStatus(PaymentStatusEnum.PAID)
                .paymentMethod("PAYOS")
                .amount(orders.getTotalAmount())
                .build();
        paymentRepository.save(payment);
        orders.setPaymentStatus(PaymentStatusEnum.PAID);
        orderRepository.save(orders);
    }

    public void createPaymentFail(Integer orderId, Integer userId) throws Exception {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Users users = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Payment payment = Payment.builder()
                .users(users)
                .orders(orders)
                .paymentCode(orders.getOrderCode().substring(3))
                .paymentDate(LocalDate.now())
                .paymentStatus(PaymentStatusEnum.UNPAID)
                .paymentMethod("PAYOS")
                .amount(orders.getTotalAmount())
                .build();
        paymentRepository.save(payment);
        orders.setStatus(OrderStatusEnum.NOT_AVAILABLE);
        orderRepository.save(orders);
    }

    public PaymentResponse createCastPayment(Integer orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Payment payment = Payment.builder()
                .users(orders.getUsers())
                .orders(orders)
                .paymentCode(orders.getOrderCode().substring(3))
                .paymentDate(LocalDate.now())
                .paymentStatus(PaymentStatusEnum.UNPAID)
                .paymentMethod("CAST")
                .amount(orders.getTotalAmount())
                .build();
        paymentRepository.save(payment);
        return convertToPaymentResponse(payment);
    }

    public List<PaymentResponse> viewByOrder(Integer orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        List<Payment> payment = paymentRepository.findByOrders(orders);
        return convertToPaymentResponseList(payment);
    }

    public List<PaymentResponse> viewByUser() {
        Users users = accountUtils.getCurrentUser();
        List<Payment> paymentList = paymentRepository.findByUsers(users);
        return convertToPaymentResponseList(paymentList);
    }

    public PaymentResponse convertToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .code(payment.getPaymentCode())
                .customerId(payment.getUsers().getId())
                .orderId(payment.getOrders().getId())
                .amount(payment.getAmount())
                .method(payment.getPaymentMethod())
                .status(payment.getPaymentStatus())
                .build();
    }

    public List<PaymentResponse> convertToPaymentResponseList(List<Payment> payments) {
        List<PaymentResponse> paymentResponseList = new ArrayList<>();
        for (Payment paymentResponse : payments) {
            paymentResponseList.add(convertToPaymentResponse(paymentResponse));
        }
        return paymentResponseList;
    }
}
