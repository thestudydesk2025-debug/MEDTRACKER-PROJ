package com.medicinetracker.controller;

import com.medicinetracker.repository.SupplierRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {
    private final SupplierRepository supplierRepository;

    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping
    public String listSuppliers(Model model) {
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "suppliers/list";
    }

    @GetMapping("/add")
    public String addSupplierForm(Model model) {
        model.addAttribute("supplier", new com.medicinetracker.entity.Supplier());
        return "suppliers/form";
    }

    @org.springframework.web.bind.annotation.PostMapping("/add")
    public String addSupplier(@org.springframework.web.bind.annotation.ModelAttribute com.medicinetracker.entity.Supplier supplier) {
        if (supplier.getStatus() == null || supplier.getStatus().isEmpty()) {
            supplier.setStatus("ACTIVE");
        }
        supplierRepository.save(supplier);
        return "redirect:/suppliers";
    }
}
