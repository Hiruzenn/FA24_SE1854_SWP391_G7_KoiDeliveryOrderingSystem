package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardResponse {
    Integer totalUser;
    Integer orderInWeek;
    Integer orderInMonth;
    Integer totalOrder;
    Double profitInWeek;
    Double profitInMonth;
    Double totalProfit;
}
