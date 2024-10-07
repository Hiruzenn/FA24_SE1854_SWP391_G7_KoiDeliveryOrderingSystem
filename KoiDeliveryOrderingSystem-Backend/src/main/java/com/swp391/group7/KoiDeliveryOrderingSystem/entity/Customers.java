package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.CustomerStatusEnum;
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

public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "customers")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "customers")
    private List<HandoverDocument> handoverDocuments;

    @OneToMany(mappedBy = "customers")
    private List<Payment> payments;

    @OneToMany(mappedBy = "customers")
    private List<Feedback> feedbacks;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "pasword", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "balance")
    private float balance;

    @Column(name = "avatar", unique = true)
    private String avatar;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "update_by")
    private Integer updateBy;

    @Column(name = "customer_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerStatusEnum customerStatus;
}
