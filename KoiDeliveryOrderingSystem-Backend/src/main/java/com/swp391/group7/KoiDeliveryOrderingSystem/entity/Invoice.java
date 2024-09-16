package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity

public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "invoice_no", nullable = false, unique = true)
    private String invoiceNo;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @OneToMany(mappedBy = "invoice")
    private List<Package> packages;

    @OneToMany(mappedBy = "invoice")
    private List<HealCareDeliveryHistory> healCareDeliveryHistories;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customers customers;

    @Column(name = "health_care_delivery_history_id")
    private Integer healthCareDeliveryHistoryId;

    @Column(name = "invoice_description")
    private String invoiceDescription;

    @Column(name = "staff", nullable = false)
    private String staff;

    @Column(name = "vehicle",nullable = false)
    private String vehicle;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "depature", nullable = false)
    private String depature;

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

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SystemStatusEnum status;
}
