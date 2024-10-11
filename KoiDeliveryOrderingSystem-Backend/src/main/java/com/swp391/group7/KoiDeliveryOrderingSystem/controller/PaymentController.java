package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.payment.CreatePaymentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.PaymentResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("create/{orderId}")
    public ApiResponse<PaymentResponse> createPayment (@PathVariable("orderId") Integer orderId,
                                                       @RequestBody CreatePaymentRequest createPaymentRequest){
        var result = paymentService.createPayment(orderId, createPaymentRequest);
        return ApiResponse.<PaymentResponse>builder()
                .code(200)
                .message("Payment created successfully")
                .result(result)
                .build();
    }

    @GetMapping("view-all")
    public ApiResponse<List<PaymentResponse>> getAllPayments(){
        var result = paymentService.viewAllPayment();
        return ApiResponse.<List<PaymentResponse>>builder()
                .code(200)
                .message("All payments successfully")
                .result(result)
                .build();
    }

    @GetMapping("view-by-customer")
    public ApiResponse<List<PaymentResponse>> getPaymentByCustomer(){
        var result = paymentService.viewPaymentsByCustomer();
        return ApiResponse.<List<PaymentResponse>>builder()
                .code(200)
                .message("Payment by customer successfully")
                .result(result)
                .build();
    }

    @GetMapping("view-by-order-id/{orderId}")
    public ApiResponse<List<PaymentResponse>> getPaymentByOrderId(@PathVariable("orderId") Integer orderId){
        var result = paymentService.viewPaymentsByOrderId(orderId);
        return ApiResponse.<List<PaymentResponse>>builder()
                .code(200)
                .message("Payment by customer successfully")
                .result(result)
                .build();
    }
}
