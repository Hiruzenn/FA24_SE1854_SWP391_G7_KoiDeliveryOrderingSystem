package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.query.Order;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity

public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @OneToMany(mappedBy = "orderDetail")
    private List<FishProfile> fishProfiles;

    @OneToMany(mappedBy = "orderDetail")
    private List<CheckingKoiHealth> checkingKoiHealth;

    @Column(name = "quanity", nullable = false)
    private Integer quanity;

    @Column(name = "unit_price", nullable = false)
    private Float unitPrice;

    @Column(name = "amount", nullable = false)
    private Float amount;

    @Column(name = "receiving_date", nullable = false)
    private LocalDateTime receivingDate;

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
