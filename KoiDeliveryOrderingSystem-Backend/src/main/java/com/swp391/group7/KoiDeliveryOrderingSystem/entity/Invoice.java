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

public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "invoice_no", nullable = false, unique = true)
    private String invoiceNo;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonManagedReference
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonManagedReference
    private Users users;

    @OneToMany(mappedBy = "invoice")
    @JsonBackReference
    private List<HealthCareDeliveryHistory> healCareDeliveryHistories;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "address_ store")
    private String addressStore;

    @Column(name = "address_customer")
    private String addressCustomer;

    @Column(name = "vat")
    private Float vat;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "total_amount")
    private Float totalAmount;

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
