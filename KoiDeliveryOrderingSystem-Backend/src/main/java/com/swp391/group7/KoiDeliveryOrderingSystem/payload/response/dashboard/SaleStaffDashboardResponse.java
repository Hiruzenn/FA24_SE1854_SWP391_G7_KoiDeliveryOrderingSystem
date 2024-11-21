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
public class SaleStaffDashboardResponse {
    Double totalPayment;
    Integer totalHandoverDocument;
    Integer totalReportAnswered;
}