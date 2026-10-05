package com.medicinetracker.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reorders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reorder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String status; // PENDING, REQUESTED, ORDERED, RECEIVED, CANCELLED

    @Column(nullable = false)
    private String priority; // LOW, MEDIUM, HIGH, CRITICAL

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
