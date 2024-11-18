package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class FishProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "species", nullable = false)
    @JsonManagedReference
    private FishCategory fishCategory;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonManagedReference
    private Orders orders;

    @OneToMany(mappedBy = "fishProfile")
    @JsonBackReference
    private List<Certificate> certificates;

    @OneToMany(mappedBy = "fishProfile")
    @JsonBackReference
    private List<CheckingKoiHealth> checkingKoiHealth;

    @Column(name = "name", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(name = "description", columnDefinition = "NVARCHAR(255)")
    private String description;

    @Column(name = "sex", columnDefinition = "NVARCHAR(255)")
    private String sex;

    @Column(name = "size", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String size;

    @Column(name = "age")
    private Integer age;

    @Column(name = "origin", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String origin;

    @Column(name = "weight", nullable = false)
    private Float weight;

    @Column(name = "color", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String color;

    @Column(name = "image", nullable = false)
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
