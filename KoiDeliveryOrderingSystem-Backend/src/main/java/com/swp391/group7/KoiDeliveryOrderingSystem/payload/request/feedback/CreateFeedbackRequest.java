package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.feedback;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFeedbackRequest {
    @NotBlank(message = "Feedback Description is required")
    private String feedbackDescription;
    @NotBlank(message = "rating is required")
    private Integer rating;
}
