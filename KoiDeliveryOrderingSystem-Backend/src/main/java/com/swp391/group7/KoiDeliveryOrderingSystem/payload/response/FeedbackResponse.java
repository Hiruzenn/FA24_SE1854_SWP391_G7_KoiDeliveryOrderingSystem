package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackResponse {
    private Integer id;
    private String customerId;
    private String orderId;
    private Integer rating;
    private String feedbackDescription;
}
