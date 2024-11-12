package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.service.PayOSService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentLinkData;

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

    @PostMapping("Get")
    public PaymentLinkData getPayment(@RequestParam("orderId") Integer orderId) throws Exception {
        return payOSService.getPaymentLink(orderId);
    }
    @GetMapping("payment-success")
    public ModelAndView paymentSuccess(@RequestParam("orderId") Integer orderId, @RequestParam("userId") Integer userId) throws Exception {
        payOSService.createPaymentDB(orderId,userId);
        return new ModelAndView("PaymentSuccess");
    }
    @GetMapping("payment-fail")
    public ModelAndView paymentFail(){
        return new ModelAndView("PaymentFail");
    }
    @PostMapping("create-payment")
    public void createPayment(@RequestParam("orderId") Integer orderId, @RequestParam("userId") Integer userId) throws Exception {
        payOSService.createPaymentDB(orderId, userId);
    }
}
