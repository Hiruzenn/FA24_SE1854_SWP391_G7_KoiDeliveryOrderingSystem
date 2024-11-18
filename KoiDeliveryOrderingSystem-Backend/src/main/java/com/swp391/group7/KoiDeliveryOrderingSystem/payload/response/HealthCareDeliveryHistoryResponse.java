package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HealthCareDeliveryHistoryResponse {
    private Integer id;
    private Integer handoverDocumentId;
    private String route;
    private String healthDescription;
    private String eatingDescription;
    private LocalDateTime createAt;
    private Integer createBy;
    private LocalDateTime updateAt;
    private Integer updateBy;
}
