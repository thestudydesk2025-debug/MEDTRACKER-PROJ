package com.medicinetracker.repository;

import com.medicinetracker.entity.Reorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReorderRepository extends JpaRepository<Reorder, Long> {
    List<Reorder> findByStatus(String status);
}
