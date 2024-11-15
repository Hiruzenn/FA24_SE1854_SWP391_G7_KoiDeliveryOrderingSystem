package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportResponse {
    private Integer id;
    private Integer userId;
    private Integer orderId;
    private String title;
    private String description;
    private String answer;
}
