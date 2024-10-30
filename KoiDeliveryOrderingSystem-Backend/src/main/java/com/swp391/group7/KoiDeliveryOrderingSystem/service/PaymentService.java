package com.swp391.group7.KoiDeliveryOrderingSystem.service;


import com.swp391.group7.KoiDeliveryOrderingSystem.config.VNPayConfig;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Payment;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.payment.CreatePaymentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.payment.PaymentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.payment.PaymentRequest2;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.OrderResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.PaymentResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.PaymentResponse2;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.InvoiceRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;


import com.swp391.group7.KoiDeliveryOrderingSystem.repository.PaymentRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.UserRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import com.swp391.group7.KoiDeliveryOrderingSystem.vnpay.VNPayUntil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private VNPayConfig vnPayConfig;

    @Autowired
    private UserRepository userRepository;

    public static final String RANDOM_STRING = "0123456789";

    public PaymentResponse createPayment(Integer orderId, CreatePaymentRequest createPaymentRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(orderId, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
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
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(orderId, SystemStatusEnum.AVAILABLE).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        List<Payment> paymentList = paymentRepository.findByOrdersAndStatus(orders, SystemStatusEnum.AVAILABLE);
        return convertToListPaymentResponse(paymentList);
    }

    public List<PaymentResponse> viewPaymentsByCustomer() {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        List<Payment> paymentList = paymentRepository.findByUsersAndStatus(users, SystemStatusEnum.AVAILABLE);
        return convertToListPaymentResponse(paymentList);
    }

    public List<PaymentResponse> viewAllPayment(){
        List<Payment> paymentList = paymentRepository.findByStatus(SystemStatusEnum.AVAILABLE);
        return convertToListPaymentResponse(paymentList);
    }


    public PaymentRequest createVnPayPayment(PaymentRequest2 paymentRequest2, HttpServletRequest request, int orderId) {
        Orders orders = orderRepository.findById(paymentRequest2.getOrderId()).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        Users user = userRepository.findById(orders.getUsers().getId()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (user == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Object result = orderRepository.findTotalAmount(paymentRequest2.getOrderId());
        // Ensure the result is not null and is an array of Object
        if (result == null || !(result instanceof Object[])) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }


       paymentRequest2.setOrderId(orderId);
        String username =user.getName();
        float amount = paymentRequest2.getTotalAmount()*100;
        String bankCode = paymentRequest2.getBankCode();
        String transactionId = "1";

         Map<String, String> vnpParamsMap;
        if (transactionId != null) {
            int id = Integer.parseInt(transactionId);
            vnpParamsMap = vnPayConfig.getVNPayConfig(orderId, username, id);
        } else {
            vnpParamsMap = vnPayConfig.getVNPayConfig(orderId, username, 0);
        }
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUntil.getIpAddress(request));

        // Build query URL
        String queryUrl = VNPayUntil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUntil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUntil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnpPayUrl() + "?" + queryUrl;

        Payment payment = new Payment();
        payment.setUsers(user);
        payment.setOrders(orders);
        payment.setPaymentStatus("Success");
        payment.setAmount(orders.getTotalAmount());
        payment.setPaymentMethod("VNPay");
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentCode(String.valueOf(Integer.parseInt(vnpParamsMap.get("vnp_TxnRef"))));
       payment.setStatus(SystemStatusEnum.AVAILABLE);
        paymentRepository.save(payment);
        if (payment.getOrders() == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        return PaymentRequest.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();



    }

    public PaymentResponse2 handleCallback(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        String paymentCode = request.getParameter("vnp_TxnRef");
        Payment payment = paymentRepository.getPaymentByPaymentCode(paymentCode);
        if (status.equals("00")) {
            payment.setPaymentStatus("SUCCESSFUL");
            payment.setPaymentDate(LocalDate.now());
            paymentRepository.save(payment);
            return new PaymentResponse2(status, "SUCCESSFUL", convertToPaymentResponse(payment));
        }
        else {
            payment.setPaymentStatus("FAILED");
            paymentRepository.save(payment);
            return new PaymentResponse2(status, "FAILED", convertToPaymentResponse(payment));
        }
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
