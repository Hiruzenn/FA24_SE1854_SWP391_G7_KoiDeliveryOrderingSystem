package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class DeliveryMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "delivery_name")
    private String deliveryName;

    @OneToMany(mappedBy = "deliveryMethod")
    private List<Orders> orders;
}
