package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity

public class CustomsDeclaration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonManagedReference
    private Orders orders;

    @Column(name = "customs_name", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String customsName;

    @Column(name = "declaration_no", nullable = false)
    private String declarationNo;

    @Column(name = "declaration_date", nullable = false)
    private LocalDateTime declarationDate;

    @Column(name = "declaration_by", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String declarationBy;

    @Column(name = "reference_no", nullable = false)
    private String referenceNo;

    @Column(name = "reference_date", nullable = false)
    private LocalDateTime referenceDate;

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
