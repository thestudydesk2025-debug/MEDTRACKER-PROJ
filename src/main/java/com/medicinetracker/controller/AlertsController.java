package com.medicinetracker.controller;

import com.medicinetracker.repository.MedicineRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alerts")
public class AlertsController {

    private final MedicineRepository medicineRepository;

    public AlertsController(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    @GetMapping
    public String stockAlerts(Model model) {
        model.addAttribute("lowStock", medicineRepository.findLowStockMedicines());
        model.addAttribute("outOfStock", medicineRepository.findOutOfStockMedicines());
        return "alerts/index";
    }
}
