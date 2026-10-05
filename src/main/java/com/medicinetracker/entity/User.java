package com.medicinetracker.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    private String phone;
    
    @Column(nullable = false)
    private String passwordHash;
    
    @Column(nullable = false)
    private String role; // e.g., "ROLE_ADMIN", "ROLE_PHARMACY_STAFF"
    
    @Column(nullable = false)
    private boolean active = true;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    private LocalDateTime lastLogin;
}
