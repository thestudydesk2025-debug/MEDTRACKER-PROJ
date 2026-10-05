package com.medicinetracker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    private String contactPerson;
    
    private String phone;
    
    private String email;
    
    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false)
    private String status; // ACTIVE, INACTIVE
}
