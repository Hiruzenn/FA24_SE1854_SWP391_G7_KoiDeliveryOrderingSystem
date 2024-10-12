package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFeedbackRequest {
    private String feedbackDescription;
}
