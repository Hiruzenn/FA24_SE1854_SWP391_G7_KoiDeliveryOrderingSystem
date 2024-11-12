package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.config.PayOSConfig;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Payment;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.PaymentRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.UserRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentData;
import vn.payos.type.PaymentLinkData;

import java.security.SecureRandom;
import java.time.LocalDate;

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
            Users users = accountUtils.getCurrentUser();
            Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
            PayOS payOS = new PayOS(payOSConfig.getClientId(), payOSConfig.getApiKey(), payOSConfig.getChecksumKey());
            int totalAmountInt = (int) orders.getTotalAmount();
            Long orderCodeLong = Long.parseLong(orders.getOrderCode().substring(3));
            PaymentData paymentData = PaymentData.builder()
                    .amount(totalAmountInt)
                    .orderCode(orderCodeLong)
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

    public PaymentLinkData getPaymentLink(Integer orderId) throws Exception {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        PayOS payOS = new PayOS(payOSConfig.getClientId(), payOSConfig.getApiKey(), payOSConfig.getChecksumKey());
        Long orderCodeLong = Long.parseLong(orders.getOrderCode().substring(3));
        return payOS.getPaymentLinkInformation(orderCodeLong);
    }

    public void createPaymentDB(Integer orderId, Integer userId) throws Exception {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Users users = userRepository.findById(userId).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        Payment payment = Payment.builder()
                .amount(orders.getTotalAmount())
                .paymentDate(LocalDate.now())
                .paymentMethod("PAYOS")
                .paymentStatus("SUCCESS")
                .status(SystemStatusEnum.AVAILABLE)
                .paymentCode(orders.getOrderCode().substring(3))
                .users(users)
                .orders(orders)
                .build();
        paymentRepository.save(payment);
    }
}
