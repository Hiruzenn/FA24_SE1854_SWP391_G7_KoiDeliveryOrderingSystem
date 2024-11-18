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
public class ManagerDashboardResponse {
    Integer totalUser;
    Integer orderInWeek;
    Integer orderInMonth;
    Integer totalOrder;
    Double profitInWeek;
    Double profitInMonth;
    Double totalProfit;
    Integer totalReport;
    Integer totalFeedback;
}