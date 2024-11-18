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
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fish_profle", nullable = false)
    @JsonManagedReference
    private FishProfile fishProfile;

    @Column(name = "name", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(name = "award", columnDefinition = "NVARCHAR(255)")
    private String award;

    @Column(name = "species", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String species;

    @Column(name = "sex", columnDefinition = "NVARCHAR(255)")
    private String sex;

    @Column(name = "size")
    private Integer size;

    @Column(name = "age")
    private Integer age;

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
