package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReportRequest {
    private String title;
    private String description;
}
