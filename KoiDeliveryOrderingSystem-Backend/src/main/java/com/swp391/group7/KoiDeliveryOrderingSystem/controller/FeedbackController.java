package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.feedback.CreateFeedbackRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.FeedbackResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("create/{orderId}")
    public ApiResponse<FeedbackResponse> createFeedback(@PathVariable("orderId") Integer orderId,
                                                        @RequestBody CreateFeedbackRequest createFeedbackRequest) {
        var result = feedbackService.createFeedback(orderId, createFeedbackRequest);
        return ApiResponse.<FeedbackResponse>builder()
                .code(200)
                .message("Feedback created")
                .result(result)
                .build();
    }

    @GetMapping("view-all")
    public ApiResponse<List<FeedbackResponse>> viewAllFeedback() {
        var result = feedbackService.viewAllFeedback();
        return ApiResponse.<List<FeedbackResponse>>builder()
                .code(200)
                .message("View All Feedback")
                .result(result)
                .build();
    }

    @GetMapping("view-by-order-id/{orderId}")
    public ApiResponse<List<FeedbackResponse>> viewFeedBackByOrderId(@PathVariable("orderId") Integer orderId) {
        var result = feedbackService.viewFeedbackByOrderId(orderId);
        return ApiResponse.<List<FeedbackResponse>>builder()
                .code(200)
                .message("View Feedback By Order Id")
                .result(result)
                .build();
    }

    @GetMapping("view-by-customer")
    public ApiResponse<List<FeedbackResponse>> viewFeedbackByCustomer() {
        var result = feedbackService.viewFeedbackByCustomer();
        return ApiResponse.<List<FeedbackResponse>>builder()
                .code(200)
                .message("View Feedback By Customer")
                .result(result)
                .build();
    }
}
