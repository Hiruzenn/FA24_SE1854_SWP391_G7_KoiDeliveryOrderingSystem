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
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "orders_id", nullable = false)
    @JsonManagedReference
    private Orders orders;

    @Column(name = "certificate_name",nullable = false, columnDefinition = "NVARCHAR(255)")
    private String certificateName;

    @Column(name = "certificate_description", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String certificateDescription;

    @Column(name = "health", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String health;

    @Column(name = "origin", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String origin;

    @Column(name = "award", columnDefinition = "NVARCHAR(255)")
    private String award;

    @Column(name = "image")
    private String image;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "update_by")
    private Integer updateBy;

    @Column(name = "cetificate_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SystemStatusEnum status;
}
