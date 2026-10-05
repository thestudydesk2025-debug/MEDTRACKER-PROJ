package com.medicinetracker.controller;

import com.medicinetracker.entity.InventoryTransaction;
import com.medicinetracker.entity.Medicine;
import com.medicinetracker.entity.User;
import com.medicinetracker.repository.InventoryTransactionRepository;
import com.medicinetracker.repository.MedicineRepository;
import com.medicinetracker.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryTransactionRepository transactionRepository;
    private final MedicineRepository medicineRepository;
    private final UserRepository userRepository;

    public InventoryController(InventoryTransactionRepository transactionRepository, 
                               MedicineRepository medicineRepository, 
                               UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.medicineRepository = medicineRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String inventoryDashboard(Model model) {
        model.addAttribute("transactions", transactionRepository.findTop10ByOrderByCreatedAtDesc());
        model.addAttribute("medicines", medicineRepository.findAll());
        return "inventory/index";
    }

    @PostMapping("/transaction")
    public String processTransaction(@RequestParam Long medicineId,
                                     @RequestParam String type,
                                     @RequestParam Integer quantity,
                                     @RequestParam(required = false) String reason,
                                     Authentication authentication,
                                     RedirectAttributes redirectAttributes) {
        
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid medicine Id:" + medicineId));
                
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        int previousStock = medicine.getQuantity();
        int newStock = previousStock;

        if ("STOCK_IN".equals(type)) {
            newStock = previousStock + quantity;
        } else if ("STOCK_OUT".equals(type) || "EXPIRED".equals(type) || "DAMAGED".equals(type)) {
            if (previousStock < quantity) {
                redirectAttributes.addFlashAttribute("errorMessage", "Insufficient stock for this transaction.");
                return "redirect:/inventory";
            }
            newStock = previousStock - quantity;
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid transaction type.");
            return "redirect:/inventory";
        }

        medicine.setQuantity(newStock);
        medicineRepository.save(medicine);

        InventoryTransaction transaction = InventoryTransaction.builder()
                .medicine(medicine)
                .batchNumber(medicine.getBatchNumber())
                .type(type)
                .quantity(quantity)
                .previousStock(previousStock)
                .newStock(newStock)
                .reason(reason)
                .user(user)
                .build();
                
        transactionRepository.save(transaction);
        
        redirectAttributes.addFlashAttribute("successMessage", "Transaction completed successfully!");
        return "redirect:/inventory";
    }
}
