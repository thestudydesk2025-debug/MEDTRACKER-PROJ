package com.medicinetracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reorders")
public class ReorderController {
    @GetMapping
    public String index() {
        return "reorders/index";
    }
}
