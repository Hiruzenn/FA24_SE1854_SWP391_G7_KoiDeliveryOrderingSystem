package com.swp391.group7.KoiDeliveryOrderingSystem.controller;


import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.payment.CreatePaymentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.payment.PaymentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.payment.PaymentRequest2;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.PaymentResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.PaymentResponse2;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.PaymentService;
import com.swp391.group7.KoiDeliveryOrderingSystem.vnpay.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

    @PostMapping("/payment")
    public ResponseObject<PaymentRequest> pay(@RequestBody PaymentRequest2 request, HttpServletRequest httpServletRequest) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request, httpServletRequest));
    }

    @GetMapping("/return")
    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = "http://localhost:3001/PaymentSuccess";
        String urlFail = "http://localhost:3001/PaymentFailed";
        PaymentResponse2 payment = paymentService.handleCallback(request);

        if (payment.getCode().equals("00")) {
            response.sendRedirect(url);
        } else {
            response.sendRedirect(urlFail);
        }
    }


}
