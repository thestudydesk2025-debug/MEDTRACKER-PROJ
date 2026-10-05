package com.medicinetracker.repository;

import com.medicinetracker.entity.AccessKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessKeyRepository extends JpaRepository<AccessKey, Long> {
    Optional<AccessKey> findByKey(String key);
}
