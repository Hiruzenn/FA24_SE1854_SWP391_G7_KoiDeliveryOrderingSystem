package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.HealthStatusEnum;
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
    @JoinColumn(name = "fish_profile")
    @JsonManagedReference
    private FishProfile fishProfile;

    @Column(name = "heal_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private HealthStatusEnum healthStatus;

    @Column(name = "health_status_description", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String healthStatusDescription;

    @Column(name = "weight", nullable = false)
    private Float weight;

    @Column(name = "sex", columnDefinition = "NVARCHAR(255)")
    private String sex;

    @Column(name = "color", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String color;

    @Column(name = "species", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String species;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "update_by")
    private Integer updateBy;
}
