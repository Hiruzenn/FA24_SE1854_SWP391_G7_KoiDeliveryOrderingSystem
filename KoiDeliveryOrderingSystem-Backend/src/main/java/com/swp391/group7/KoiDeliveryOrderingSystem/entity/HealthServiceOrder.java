package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

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

public class HealthServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "health_service_category_id")
    private HealthServiceCategory healthServiceCategory;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "update_by")
    private Integer updateBy;

    @Column(name = "health_service_order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SystemStatusEnum status;

}
