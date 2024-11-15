package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.PackageStatusEnum;
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

public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonManagedReference
    private Orders orders;

    @Column(name = "package_no", nullable = false)
    private String packageNo;

    @Column(name = "package_description", columnDefinition = "NVARCHAR(255)")
    private String packageDescription;

    @Column(name = "package_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PackageStatusEnum packageStatus;

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
}
