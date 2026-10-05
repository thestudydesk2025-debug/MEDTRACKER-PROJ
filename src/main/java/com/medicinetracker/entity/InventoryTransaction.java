package com.medicinetracker.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    private String batchNumber;

    @Column(nullable = false)
    private String type; // STOCK_IN, STOCK_OUT, ADJUSTMENT, RETURN, EXPIRED, DAMAGED

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer previousStock;

    @Column(nullable = false)
    private Integer newStock;

    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
