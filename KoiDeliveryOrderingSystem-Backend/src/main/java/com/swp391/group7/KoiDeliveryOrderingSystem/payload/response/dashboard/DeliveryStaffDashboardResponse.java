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
public class DeliveryStaffDashboardResponse {
    Integer totalHandover;
    Integer totalCheckingKoi;
    Integer totalPackage;
    Integer totalDeliveryHistory;
    Double totalDistanceDelivered;
}
