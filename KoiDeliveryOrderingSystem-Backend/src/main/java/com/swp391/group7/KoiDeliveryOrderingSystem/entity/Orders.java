package com.swp391.group7.KoiDeliveryOrderingSystem.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id", nullable = false)
    private Integer id;

    @Column(name = "order_code", nullable = false, unique = true)
    private String orderCode;

    @OneToMany(mappedBy = "orders")
    @JsonBackReference
    private List<Invoice> invoice;

    @OneToMany(mappedBy = "orders")
    @JsonBackReference
    private List<HealthServiceOrder> healthServiceOrders;

    @OneToMany(mappedBy = "orders")
    @JsonBackReference
    private List<OrderDetail> orderDetail;

    @OneToMany(mappedBy = "orders")
    @JsonBackReference
    private List<HandoverDocument> handoverDocuments;

    @OneToMany(mappedBy = "orders")
    @JsonBackReference
    private List<Certificate> certificate;

    @OneToMany(mappedBy = "orders")
    @JsonBackReference
    private List<Payment> payments;

    @OneToMany(mappedBy = "orders")
    @JsonBackReference
    private List<CustomsDeclaration> customsDeclarations;

    @OneToMany(mappedBy = "orders")
    @JsonBackReference
    private List<Feedback> feedbacks;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonManagedReference
    private Users users;

    @ManyToOne
    @JoinColumn(name = "delivery_method", nullable = false)
    @JsonManagedReference
    private DeliveryMethod deliveryMethod;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "estimate_delivery_date")
    private LocalDateTime estimateDeliveryDate;

    @Column(name = "receiving_date")
    private LocalDateTime receivingDate;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "departure", nullable = false)
    private String departure;

    @Column(name = "distance", nullable = false)
    private float distance;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "amount", nullable = false)
    private float amount;

    @Column(name = "VAT")
    private float vat;

    @Column(name = "VAT_amount")
    private float vatAmount;

    @Column(name = "total_amount", nullable = false)
    private float totalAmount;

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
