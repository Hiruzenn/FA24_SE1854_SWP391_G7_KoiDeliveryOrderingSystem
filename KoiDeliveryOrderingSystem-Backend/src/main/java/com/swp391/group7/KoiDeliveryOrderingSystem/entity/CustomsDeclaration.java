package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

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

    @Column(name = "declaration_no", nullable = false)
    private String declarationNo;

    @Column(name = "declaration_date", nullable = false)
    private LocalDateTime declarationDate;

    @Column(name = "declaration_by", nullable = false)
    private String declaratonBy;

    @Column(name = "reference_no", nullable = false)
    private String referenceNo;

    @Column(name = "reference_date", nullable = false)
    private LocalDateTime referenceeDate;

    @Column(name = "customs_name", nullable = false)
    private String customsName;

    @Column(name = "image")
    private String image;

}
