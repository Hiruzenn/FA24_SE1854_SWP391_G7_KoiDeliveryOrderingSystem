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

public class Dashboard {

    @ManyToOne
    @JoinColumn(name = "role", nullable = false, referencedColumnName = "role_name")
    @JsonManagedReference
    private Users users;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonManagedReference
    private Orders orders;




    @Column(name = "weekOrder")
    private String weekOrder;

    @Column(name = "monthOrder")
    private String monthOrder;

    @Column(name = "yearOrder")
    private String yearOrder;

    @Column(name = "weekRevenue")
    private Float weekRevenue;

    @Column(name = "monthRevenue")
    private Float monthRevenue;

    @Column(name = "yearRevenue")
    private Float yearRevenue;

    @Enumerated(EnumType.STRING)
    private SystemStatusEnum status;

}