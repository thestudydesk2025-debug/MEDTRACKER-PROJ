package com.medicinetracker.controller;

import com.medicinetracker.repository.MedicineRepository;
import com.medicinetracker.repository.SupplierRepository;
import com.medicinetracker.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final MedicineRepository medicineRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;

    public DashboardController(MedicineRepository medicineRepository, 
                               SupplierRepository supplierRepository, 
                               UserRepository userRepository) {
        this.medicineRepository = medicineRepository;
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        
        long totalMedicines = medicineRepository.count();
        long totalStock = medicineRepository.sumTotalStock();
        long totalSuppliers = supplierRepository.count();
        long lowStockCount = medicineRepository.findLowStockMedicines().size();
        long outOfStockCount = medicineRepository.findOutOfStockMedicines().size();
        long expiringSoonCount = medicineRepository.findExpiringSoonMedicines().size();
        long expiredCount = medicineRepository.findExpiredMedicines().size();
        
        // Fetch medicines for the table (low stock and expired)
        var medicines = medicineRepository.findAttentionRequiredMedicines();
        
        model.addAttribute("today", java.time.LocalDate.now());
        model.addAttribute("totalMedicines", totalMedicines);
        model.addAttribute("totalStock", totalStock);
        model.addAttribute("totalSuppliers", totalSuppliers);
        model.addAttribute("lowStockCount", lowStockCount);
        model.addAttribute("outOfStockCount", outOfStockCount);
        model.addAttribute("expiringSoonCount", expiringSoonCount);
        model.addAttribute("expiredCount", expiredCount);
        model.addAttribute("medicines", medicines);
        
        return "dashboard/index";
    }
}
