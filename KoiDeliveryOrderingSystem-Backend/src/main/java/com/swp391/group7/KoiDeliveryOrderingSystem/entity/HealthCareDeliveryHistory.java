package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JoinColumn(name = "handover_document_id")
    @JsonManagedReference
    private HandoverDocument handoverDocument;

    @Column(name = "route", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String route;

    @Column(name = "health_description", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String healthDescription;

    @Column(name = "eating_description", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String eatingDescription;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "update_by")
    private Integer updateBy;
}
