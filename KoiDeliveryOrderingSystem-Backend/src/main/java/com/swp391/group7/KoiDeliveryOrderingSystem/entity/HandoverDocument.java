package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

public class HandoverDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonManagedReference
    private Users users;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "package_id", referencedColumnName = "id")
    @JsonManagedReference
    private Package packages;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonManagedReference
    private Orders orders;

    @OneToMany(mappedBy = "handoverDocument")
    @JsonBackReference
    private List<HealthCareDeliveryHistory> healthCareDeliveryHistory;

    @Column(name = "handover_no", nullable = false)
    private String handoverNo;

    @Column(name = "staff", nullable = false)
    private String staff;

    @Column(name = "handover_description")
    private String handoverDescription;

    @Column(name = "vehicle", nullable = false)
    private String vehicle;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "departure", nullable = false)
    private String departure;

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
