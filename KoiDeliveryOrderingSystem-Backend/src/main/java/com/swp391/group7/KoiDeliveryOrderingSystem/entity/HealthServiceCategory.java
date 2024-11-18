package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

public class HealthServiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "healthServiceCategory")
    @JsonBackReference
    private List<HealthServiceOrder> healthServiceOrder;

    @Column(name = "service_name", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String serviceName;

    @Column(name = "service_description", columnDefinition = "NVARCHAR(255)")
    private String serviceDescription;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "update_by")
    private Integer updateBy;
}
