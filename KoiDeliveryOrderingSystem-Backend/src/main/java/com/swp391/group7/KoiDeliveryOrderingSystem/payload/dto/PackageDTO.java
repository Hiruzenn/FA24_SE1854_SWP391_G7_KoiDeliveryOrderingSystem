package com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PackageDTO {
    private Integer id;
    private String packageNo;
    private String packageDescription;
    private String packageDate;
    private String packageStatus;
    private String packageBy;
    private Integer invoiceId;
    private int checkingKoiHealthId;
}
