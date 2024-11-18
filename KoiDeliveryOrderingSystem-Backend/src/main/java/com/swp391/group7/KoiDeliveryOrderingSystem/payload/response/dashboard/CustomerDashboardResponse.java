package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.dashboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDashboardResponse {
    Integer availableOrder;
    Integer inProgressOrder;
    Integer completedOrder;
    Integer totalFishProfile;
    Double totalPaid;
    Integer totalReport;
    Integer totalFeedback;
}
