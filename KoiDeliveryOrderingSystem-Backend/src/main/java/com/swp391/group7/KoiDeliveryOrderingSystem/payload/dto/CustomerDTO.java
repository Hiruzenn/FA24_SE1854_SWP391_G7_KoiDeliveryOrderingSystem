package com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String address;
    private float balance;
    private String avatar;
    private int invoices;
    private int handoverDocuments;
    private int payments;
    private int feedbacks;
    private int orders;
}
