package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.HandoverStatusEnum;
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

public class HandoverDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonManagedReference
    private Users users;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonManagedReference
    private Orders orders;

    @OneToMany(mappedBy = "handoverDocument")
    @JsonBackReference
    private List<HealthCareDeliveryHistory> healthCareDeliveryHistory;

    @Column(name = "handover_no", nullable = false)
    private String handoverNo;

    @Column(name = "handover_description", columnDefinition = "NVARCHAR(255)")
    private String handoverDescription;

    @Column(name = "vehicle", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String vehicle;

    @Column(name = "destination", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String destination;

    @Column(name = "departure", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String departure;

    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

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

    @Column(name = "handover_status")
    @Enumerated(EnumType.STRING)
    private HandoverStatusEnum handoverStatus;
}
