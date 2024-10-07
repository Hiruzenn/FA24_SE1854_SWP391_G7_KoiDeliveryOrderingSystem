package com.swp391.group7.KoiDeliveryOrderingSystem.entity;


import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders orders;

    @Column(name = "certificate_name",nullable = false)
    private String certificateName;

    @Column(name = "certificate_description", nullable = false)
    private String certificateDescription;

    @Column(name = "health", nullable = false)
    private String health;

    @Column(name = "origin", nullable = false)
    private String origin;

    @Column(name = "award")
    private String award;

    @Column(name = "image")
    private String image;
}
