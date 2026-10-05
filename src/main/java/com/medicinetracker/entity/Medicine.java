package com.medicinetracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "medicines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String genericName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String batchNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer minimumStock;

    private Integer maximumStock;

    @Column(precision = 10, scale = 2)
    private BigDecimal unitPrice;

    private LocalDate manufacturingDate;

    @Column(nullable = false)
    private LocalDate expiryDate;

    private String storageLocation;

    @Column(nullable = false)
    private String status; // ACTIVE, INACTIVE

    @Column(columnDefinition = "TEXT")
    private String description;
}
