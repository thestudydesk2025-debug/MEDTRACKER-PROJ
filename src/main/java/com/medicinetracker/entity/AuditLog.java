package com.medicinetracker.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String action; // LOGIN, LOGOUT, CREATE_MEDICINE, UPDATE_MEDICINE, DELETE_MEDICINE, STOCK_IN, STOCK_OUT, etc.

    @Column(nullable = false)
    private String entity; // User, Medicine, Supplier, etc.

    private String entityId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    private LocalDateTime timestamp;
}
