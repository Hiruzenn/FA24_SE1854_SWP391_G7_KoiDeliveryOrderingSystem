package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "users")
    @JsonBackReference
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "users")
    @JsonBackReference
    private List<HandoverDocument> handoverDocuments;

    @OneToMany(mappedBy = "users")
    @JsonBackReference
    private List<Payment> payments;

    @OneToMany(mappedBy = "users")
    @JsonBackReference
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "users")
    @JsonBackReference
    private List<Orders> orders ;

    @ManyToOne
    @JoinColumn(name = "role", nullable = false)
    @JsonManagedReference
    private Role role;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "pasword", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "balance")
    private float balance;

    @Column(name = "avatar", unique = true)
    private String avatar;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "update_by")
    private Integer updateBy;

    @Column(name = "customer_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerStatusEnum customerStatus;
}
