package com.medicinetracker.controller;

import com.medicinetracker.entity.Medicine;
import com.medicinetracker.repository.CategoryRepository;
import com.medicinetracker.repository.MedicineRepository;
import com.medicinetracker.repository.SupplierRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/medicines")
public class MedicineController {

    private final MedicineRepository medicineRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    public MedicineController(MedicineRepository medicineRepository, 
                              CategoryRepository categoryRepository, 
                              SupplierRepository supplierRepository) {
        this.medicineRepository = medicineRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    @GetMapping
    public String listMedicines(@RequestParam(value = "search", required = false) String search, Model model) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("medicines", medicineRepository.findByNameContainingIgnoreCaseOrGenericNameContainingIgnoreCase(search.trim(), search.trim()));
            model.addAttribute("searchQuery", search.trim());
        } else {
            model.addAttribute("medicines", medicineRepository.findAll());
        }
        return "medicines/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("medicine", new Medicine());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "medicines/form";
    }

    @PostMapping("/save")
    public String saveMedicine(@Valid @ModelAttribute("medicine") Medicine medicine,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("suppliers", supplierRepository.findAll());
            return "medicines/form";
        }
        
        medicineRepository.save(medicine);
        redirectAttributes.addFlashAttribute("successMessage", "Medicine saved successfully!");
        return "redirect:/medicines";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid medicine Id:" + id));
                
        model.addAttribute("medicine", medicine);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "medicines/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid medicine Id:" + id));
        medicineRepository.delete(medicine);
        redirectAttributes.addFlashAttribute("successMessage", "Medicine deleted successfully!");
        return "redirect:/medicines";
    }
}
