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

    @OneToMany(mappedBy = "packages")
    @JsonBackReference
    private List<CheckingKoiHealth> checkingKoiHealth;

    @OneToOne(mappedBy = "packages")
    @JsonBackReference
    private HandoverDocument handoverDocument;

    @Column(name = "package_no", nullable = false)
    private String packageNo;

    @Column(name = "package_description")
    private String packageDescription;

    @Column(name = "package_date", nullable = false)
    private String packageDate;

    @Column(name = "package_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PackageStatusEnum packageStatusEnum;

    @Column(name = "package_by", nullable = false)
    private String packageBy;

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
