package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.InvoiceStatusEnum;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "address_ store", columnDefinition = "NVARCHAR(255)")
    private String addressStore;

    @Column(name = "address_customer", columnDefinition = "NVARCHAR(255)")
    private String addressCustomer;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "vat")
    private Float vat;

    @Column(name = "vat_amount")
    private Float vatAmount;

    @Column(name = "total_amount")
    private Float totalAmount;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceStatusEnum status;
}
