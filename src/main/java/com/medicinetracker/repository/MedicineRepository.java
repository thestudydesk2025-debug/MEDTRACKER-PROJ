package com.medicinetracker.repository;

import com.medicinetracker.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    
    @Query("SELECT m FROM Medicine m WHERE m.quantity <= m.minimumStock")
    List<Medicine> findLowStockMedicines();
    
    @Query("SELECT m FROM Medicine m WHERE m.quantity = 0")
    List<Medicine> findOutOfStockMedicines();
    
    @Query("SELECT m FROM Medicine m WHERE m.expiryDate < CURRENT_DATE")
    List<Medicine> findExpiredMedicines();
    
    @Query(value = "SELECT * FROM medicines WHERE expiry_date BETWEEN CURRENT_DATE() AND DATEADD(DAY, 30, CURRENT_DATE())", nativeQuery = true)
    List<Medicine> findExpiringSoonMedicines();
    @Query("SELECT m FROM Medicine m WHERE m.quantity <= m.minimumStock OR m.expiryDate < CURRENT_DATE")
    List<Medicine> findAttentionRequiredMedicines();

    @Query("SELECT COALESCE(SUM(m.quantity), 0) FROM Medicine m")
    Long sumTotalStock();

    long countByStatus(String status);
    
    long countByCategoryId(Long categoryId);
    
    List<Medicine> findByNameContainingIgnoreCaseOrGenericNameContainingIgnoreCase(String name, String genericName);
}
