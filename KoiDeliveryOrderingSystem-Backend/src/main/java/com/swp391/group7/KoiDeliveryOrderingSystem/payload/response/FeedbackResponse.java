package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackResponse {
    private Integer id;
    private Integer customerId;
    private Integer orderId;
    private Integer rating;
    private String feedbackDescription;
}
