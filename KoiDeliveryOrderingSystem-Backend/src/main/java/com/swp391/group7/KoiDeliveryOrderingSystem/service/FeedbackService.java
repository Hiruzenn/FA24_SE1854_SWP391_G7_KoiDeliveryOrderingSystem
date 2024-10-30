package com.swp391.group7.KoiDeliveryOrderingSystem.service;


import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Feedback;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.feedback.CreateFeedbackRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.FeedbackResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.FeedbackRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountUtils accountUtils;

    public FeedbackResponse createFeedback(Integer orderId, CreateFeedbackRequest createFeedbackRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(orderId, OrderStatusEnum.AVAILABLE).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        Feedback feedback = Feedback.builder()
                .users(users)
                .orders(orders)
                .feedbackDescription(createFeedbackRequest.getFeedbackDescription())
                .build();
        feedbackRepository.save(feedback);
        return convertToFeedbackResponse(feedback);
    }

    public List<FeedbackResponse> viewAllFeedback() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return convertToListFeedbackResponse(feedbacks);
    }

    public List<FeedbackResponse> viewFeedbackByOrderId(Integer orderId) {
        Orders orders = orderRepository.findByIdAndStatus(orderId, OrderStatusEnum.AVAILABLE).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        List<Feedback> feedbacks = feedbackRepository.findByOrders(orders);
        return convertToListFeedbackResponse(feedbacks);
    }

    public List<FeedbackResponse> viewFeedbackByCustomer() {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        List<Feedback> feedbacks = feedbackRepository.findByUsers(users);
        return convertToListFeedbackResponse(feedbacks);
    }

    public List<FeedbackResponse> convertToListFeedbackResponse(List<Feedback> feedbacks) {
        List<FeedbackResponse> feedbackResponses = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            feedbackResponses.add(convertToFeedbackResponse(feedback));
        }
        return feedbackResponses;
    }

    public FeedbackResponse convertToFeedbackResponse(Feedback feedback) {
        return FeedbackResponse.builder()
                .id(feedback.getId())
                .customerId(feedback.getUsers().getId())
                .orderId(feedback.getOrders().getId())
                .feedbackDescription(feedback.getFeedbackDescription())
                .build();
    }
}
