package com.medicinetracker.controller;

import com.medicinetracker.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final com.medicinetracker.repository.MedicineRepository medicineRepository;

    public CategoryController(CategoryRepository categoryRepository, com.medicinetracker.repository.MedicineRepository medicineRepository) {
        this.categoryRepository = categoryRepository;
        this.medicineRepository = medicineRepository;
    }

    @GetMapping
    public String listCategories(Model model) {
        var categories = categoryRepository.findAll();
        java.util.Map<Long, Long> categoryCounts = new java.util.HashMap<>();
        for (var cat : categories) {
            categoryCounts.put(cat.getId(), medicineRepository.countByCategoryId(cat.getId()));
        }
        
        model.addAttribute("categories", categories);
        model.addAttribute("categoryCounts", categoryCounts);
        return "categories/list";
    }
}
