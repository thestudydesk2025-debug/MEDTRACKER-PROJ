package com.medicinetracker.controller;

import com.medicinetracker.repository.InventoryTransactionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Sort;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
    private final InventoryTransactionRepository txRepository;

    public TransactionController(InventoryTransactionRepository txRepository) {
        this.txRepository = txRepository;
    }

    @GetMapping
    public String listTransactions(Model model) {
        model.addAttribute("transactions", txRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")));
        return "transactions/list";
    }
}
