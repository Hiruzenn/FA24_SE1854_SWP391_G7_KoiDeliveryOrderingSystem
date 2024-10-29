package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.HealthStatusEnum;
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
public class CheckingKoiHealth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_detail_id")
    @JsonManagedReference
    private OrderDetail orderDetail;

    @ManyToOne
    @JoinColumn(name = "package_id")
    @JsonManagedReference
    private Package packages;

    @Column(name = "heal_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private HealthStatusEnum healthStatus;

    @Column(name = "health_status_description", nullable = false)
    private String healthStatusDescription;

    @Column(name = "weight", nullable = false)
    private Float weight;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "update_by")
    private Integer updateBy;

    @Column(name = "checking_koi_health_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SystemStatusEnum status;
}
