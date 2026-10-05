package com.medicinetracker.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "access_keys")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access_key", nullable = false, unique = true)
    private String key;

    @Column(nullable = false)
    private String status; // ACTIVE, USED, INACTIVE

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "used_by_user_id")
    private User usedBy;
}
