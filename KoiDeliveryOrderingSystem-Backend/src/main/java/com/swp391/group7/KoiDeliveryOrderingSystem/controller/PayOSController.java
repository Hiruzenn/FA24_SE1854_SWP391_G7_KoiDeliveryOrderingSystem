package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.PaymentResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.PayOSService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentLinkData;

import java.util.List;

@RestController
@RequestMapping("/payos")
@RequiredArgsConstructor
public class PayOSController {
    @Autowired
    private PayOSService payOSService;

    @PostMapping("create")
    public CheckoutResponseData createPayment(@RequestParam("orderId") Integer orderId) throws Exception {
        return payOSService.createPayment(orderId);
    }

    @GetMapping("view-by-order/{orderId}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> viewByOrder(@PathVariable("orderId") Integer orderId) {
        var result = payOSService.viewByOrder(orderId);
        return ResponseEntity.ok(ApiResponse.<List<PaymentResponse>>builder()
                .code(200)
                .message("Payment List by Order")
                .result(result)
                .build());
    }

    @GetMapping("view-by-user")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> viewByUser() {
        var result = payOSService.viewByUser();
        return ResponseEntity.ok(ApiResponse.<List<PaymentResponse>>builder()
                .code(200)
                .message("Payment List by User")
                .result(result)
                .build());
    }
    @GetMapping("payment-success")
    public ModelAndView paymentSuccess(@RequestParam("orderId") Integer orderId, @RequestParam("userId") Integer userId) throws Exception {
        payOSService.createPaymentSuccess(orderId, userId);
        return new ModelAndView("PaymentSuccess");
    }

    @GetMapping("payment-fail")
    public ModelAndView paymentFail(@RequestParam("orderId") Integer orderId, @RequestParam("userId") Integer userId) throws Exception {
        payOSService.createPaymentFail(orderId, userId);
        return new ModelAndView("PaymentFail");
    }

    @PostMapping("create-cast/{orderId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> createCastPayment(@PathVariable("orderId") Integer orderId) {
        var result = payOSService.createCastPayment(orderId);
        return ResponseEntity.ok(ApiResponse.<PaymentResponse>builder()
                .code(200)
                .message("Create Cast Payment")
                .result(result)
                .build());
    }

}
