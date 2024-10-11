package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity

public class HealthCareDeliveryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonManagedReference
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "handover_document_id")
    @JsonManagedReference
    private HandoverDocument handoverDocument;

    @Column(name = "route", nullable = false)
    private String route;

    @Column(name = "health_description", nullable = false)
    private String healthDescription;

    @Column(name = "eating_description", nullable = false)
    private String eatingDescription;

    @Column(name = "delivery_status", nullable = false)
    private SystemStatusEnum deliveryStatus;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SystemStatusEnum status;

}
