package com.medicinetracker.config;

import com.medicinetracker.entity.*;
import com.medicinetracker.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            AccessKeyRepository accessKeyRepository,
            CategoryRepository categoryRepository,
            SupplierRepository supplierRepository,
            MedicineRepository medicineRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            // Create Admin User if not exists
            if (!userRepository.existsByUsername("admin")) {
                User admin = User.builder()
                        .fullName("System Administrator")
                        .username("admin")
                        .email("admin@medicinetracker.com")
                        .passwordHash(passwordEncoder.encode("admin123"))
                        .role("ROLE_ADMIN")
                        .active(true)
                        .build();
                userRepository.save(admin);
            }

            // Create Staff User if not exists
            if (!userRepository.existsByUsername("staff")) {
                User staff = User.builder()
                        .fullName("Pharmacy Staff")
                        .username("staff")
                        .email("staff@medicinetracker.com")
                        .passwordHash(passwordEncoder.encode("staff123"))
                        .role("ROLE_PHARMACY_STAFF")
                        .active(true)
                        .build();
                userRepository.save(staff);
            }

            // Create Access Key if none exists
            if (accessKeyRepository.count() == 0) {
                AccessKey key = AccessKey.builder()
                        .key("MEDTRACK2026")
                        .status("ACTIVE")
                        .build();
                accessKeyRepository.save(key);
            }

            // Initialize Categories
            if (categoryRepository.count() == 0) {
                categoryRepository.save(Category.builder().name("Antibiotics").description("Used to treat or prevent some types of bacterial infection").build());
                categoryRepository.save(Category.builder().name("Painkillers").description("Drugs used to relieve pain").build());
                categoryRepository.save(Category.builder().name("Antipyretics").description("Substances that reduce fever").build());
                categoryRepository.save(Category.builder().name("Vitamins").description("Essential vitamins and supplements").build());
                categoryRepository.save(Category.builder().name("Emergency").description("Critical care and emergency medicines").build());
            }

            // Initialize Suppliers
            if (supplierRepository.count() == 0) {
                supplierRepository.save(Supplier.builder().companyName("PharmaCorp Global").contactPerson("John Doe").phone("1-800-555-0199").email("contact@pharmacorp.com").address("123 Pharma Way, Med City").status("ACTIVE").build());
                supplierRepository.save(Supplier.builder().companyName("MediSupply Inc.").contactPerson("Jane Smith").phone("1-800-555-0188").email("info@medisupply.com").address("456 Health Ave, Care Town").status("ACTIVE").build());
            }

            // Initialize Medicines
            if (medicineRepository.count() < 15) {
                // Fetch Categories
                Category antibiotics = categoryRepository.findAll().stream().filter(c -> c.getName().equals("Antibiotics")).findFirst().orElse(null);
                Category painkillers = categoryRepository.findAll().stream().filter(c -> c.getName().equals("Painkillers")).findFirst().orElse(null);
                Category antipyretics = categoryRepository.findAll().stream().filter(c -> c.getName().equals("Antipyretics")).findFirst().orElse(null);
                Category vitamins = categoryRepository.findAll().stream().filter(c -> c.getName().equals("Vitamins")).findFirst().orElse(null);
                Category emergency = categoryRepository.findAll().stream().filter(c -> c.getName().equals("Emergency")).findFirst().orElse(null);

                // Fetch Suppliers
                Supplier pharmaCorp = supplierRepository.findAll().stream().filter(s -> s.getCompanyName().equals("PharmaCorp Global")).findFirst().orElse(null);
                Supplier mediSupply = supplierRepository.findAll().stream().filter(s -> s.getCompanyName().equals("MediSupply Inc.")).findFirst().orElse(null);

                if (antibiotics != null && painkillers != null && antipyretics != null && vitamins != null && emergency != null && pharmaCorp != null && mediSupply != null) {
                    
                    // 1. Amoxicillin (Antibiotics) - Normal
                    medicineRepository.save(Medicine.builder().name("Amoxicillin 500mg").genericName("Amoxicillin").category(antibiotics).batchNumber("AMX-2026-001").supplier(pharmaCorp).quantity(1500).minimumStock(200).unitPrice(new BigDecimal("0.50")).manufacturingDate(LocalDate.now().minusMonths(6)).expiryDate(LocalDate.now().plusMonths(18)).storageLocation("A1-Shelf-2").status("ACTIVE").build());
                    
                    // 2. Paracetamol (Painkillers) - Normal
                    medicineRepository.save(Medicine.builder().name("Paracetamol 500mg").genericName("Paracetamol").category(painkillers).batchNumber("PAR-2026-002").supplier(pharmaCorp).quantity(2000).minimumStock(500).unitPrice(new BigDecimal("0.10")).manufacturingDate(LocalDate.now().minusMonths(3)).expiryDate(LocalDate.now().plusMonths(24)).storageLocation("A2-Shelf-1").status("ACTIVE").build());

                    // 3. Ibuprofen (Painkillers) - Low Stock
                    medicineRepository.save(Medicine.builder().name("Ibuprofen 400mg").genericName("Ibuprofen").category(painkillers).batchNumber("IBU-2026-003").supplier(pharmaCorp).quantity(50).minimumStock(100).unitPrice(new BigDecimal("0.20")).manufacturingDate(LocalDate.now().minusMonths(12)).expiryDate(LocalDate.now().plusMonths(12)).storageLocation("A2-Shelf-3").status("ACTIVE").build());

                    // 4. Aspirin (Painkillers) - Expiring Soon (15 days)
                    medicineRepository.save(Medicine.builder().name("Aspirin 75mg").genericName("Aspirin").category(painkillers).batchNumber("ASP-2026-004").supplier(pharmaCorp).quantity(300).minimumStock(100).unitPrice(new BigDecimal("0.15")).manufacturingDate(LocalDate.now().minusMonths(34)).expiryDate(LocalDate.now().plusDays(15)).storageLocation("B1-Shelf-1").status("ACTIVE").build());

                    // 5. Diclofenac (Painkillers) - Expired (10 days ago)
                    medicineRepository.save(Medicine.builder().name("Diclofenac 50mg").genericName("Diclofenac").category(painkillers).batchNumber("DIC-2025-005").supplier(pharmaCorp).quantity(150).minimumStock(50).unitPrice(new BigDecimal("0.30")).manufacturingDate(LocalDate.now().minusMonths(40)).expiryDate(LocalDate.now().minusDays(10)).storageLocation("B1-Shelf-2").status("ACTIVE").build());

                    // 6. Tramadol (Painkillers) - Out of stock
                    medicineRepository.save(Medicine.builder().name("Tramadol 50mg").genericName("Tramadol").category(painkillers).batchNumber("TRA-2025-006").supplier(pharmaCorp).quantity(0).minimumStock(50).unitPrice(new BigDecimal("0.80")).manufacturingDate(LocalDate.now().minusMonths(10)).expiryDate(LocalDate.now().plusMonths(10)).storageLocation("C1-Safe").status("ACTIVE").build());
                    
                    // 7. Ciprofloxacin 500mg (Antibiotics) - Normal
                    medicineRepository.save(Medicine.builder().name("Ciprofloxacin 500mg").genericName("Ciprofloxacin").category(antibiotics).batchNumber("CIP-2026-007").supplier(mediSupply).quantity(800).minimumStock(150).unitPrice(new BigDecimal("1.20")).manufacturingDate(LocalDate.now().minusMonths(4)).expiryDate(LocalDate.now().plusMonths(20)).storageLocation("A1-Shelf-1").status("ACTIVE").build());

                    // 8. Azithromycin 250mg (Antibiotics) - Expiring soon (5 days)
                    medicineRepository.save(Medicine.builder().name("Azithromycin 250mg").genericName("Azithromycin").category(antibiotics).batchNumber("AZI-2026-008").supplier(mediSupply).quantity(400).minimumStock(100).unitPrice(new BigDecimal("2.50")).manufacturingDate(LocalDate.now().minusMonths(23)).expiryDate(LocalDate.now().plusDays(5)).storageLocation("A1-Shelf-3").status("ACTIVE").build());

                    // 9. Cephalexin 500mg (Antibiotics) - Normal
                    medicineRepository.save(Medicine.builder().name("Cephalexin 500mg").genericName("Cephalexin").category(antibiotics).batchNumber("CEP-2026-009").supplier(pharmaCorp).quantity(1000).minimumStock(200).unitPrice(new BigDecimal("0.75")).manufacturingDate(LocalDate.now().minusMonths(2)).expiryDate(LocalDate.now().plusMonths(22)).storageLocation("A1-Shelf-4").status("ACTIVE").build());

                    // 10. Vitamin C 1000mg (Vitamins) - Normal
                    medicineRepository.save(Medicine.builder().name("Vitamin C 1000mg").genericName("Ascorbic Acid").category(vitamins).batchNumber("VITC-2026-010").supplier(mediSupply).quantity(3000).minimumStock(500).unitPrice(new BigDecimal("0.05")).manufacturingDate(LocalDate.now().minusMonths(1)).expiryDate(LocalDate.now().plusMonths(35)).storageLocation("V1-Shelf-1").status("ACTIVE").build());

                    // 11. Vitamin D3 5000 IU (Vitamins) - Low Stock
                    medicineRepository.save(Medicine.builder().name("Vitamin D3 5000 IU").genericName("Cholecalciferol").category(vitamins).batchNumber("VITD-2026-011").supplier(mediSupply).quantity(40).minimumStock(200).unitPrice(new BigDecimal("0.12")).manufacturingDate(LocalDate.now().minusMonths(5)).expiryDate(LocalDate.now().plusMonths(19)).storageLocation("V1-Shelf-2").status("ACTIVE").build());

                    // 12. B-Complex Forte (Vitamins) - Normal
                    medicineRepository.save(Medicine.builder().name("B-Complex Forte").genericName("Vitamin B Complex").category(vitamins).batchNumber("VITB-2026-012").supplier(pharmaCorp).quantity(1500).minimumStock(300).unitPrice(new BigDecimal("0.08")).manufacturingDate(LocalDate.now().minusMonths(8)).expiryDate(LocalDate.now().plusMonths(16)).storageLocation("V1-Shelf-3").status("ACTIVE").build());

                    // 13. Multivitamin Syrup (Vitamins) - Expired (2 days ago)
                    medicineRepository.save(Medicine.builder().name("Multivitamin Syrup").genericName("Multivitamins").category(vitamins).batchNumber("MVIT-2025-013").supplier(mediSupply).quantity(200).minimumStock(50).unitPrice(new BigDecimal("3.50")).manufacturingDate(LocalDate.now().minusMonths(24)).expiryDate(LocalDate.now().minusDays(2)).storageLocation("V2-Shelf-1").status("ACTIVE").build());

                    // 14. Epinephrine Auto-Injector (Emergency) - Normal
                    medicineRepository.save(Medicine.builder().name("Epinephrine Auto-Injector").genericName("Epinephrine").category(emergency).batchNumber("EPI-2026-014").supplier(pharmaCorp).quantity(50).minimumStock(10).unitPrice(new BigDecimal("45.00")).manufacturingDate(LocalDate.now().minusMonths(3)).expiryDate(LocalDate.now().plusMonths(9)).storageLocation("E1-Fridge").status("ACTIVE").build());

                    // 15. Atropine Sulfate (Emergency) - Expiring Soon (8 days)
                    medicineRepository.save(Medicine.builder().name("Atropine Sulfate 1mg").genericName("Atropine").category(emergency).batchNumber("ATR-2026-015").supplier(mediSupply).quantity(100).minimumStock(20).unitPrice(new BigDecimal("5.50")).manufacturingDate(LocalDate.now().minusMonths(23)).expiryDate(LocalDate.now().plusDays(8)).storageLocation("E1-Shelf-1").status("ACTIVE").build());

                    // 16. Naloxone 4mg (Emergency) - Out of stock
                    medicineRepository.save(Medicine.builder().name("Naloxone 4mg Nasal").genericName("Naloxone").category(emergency).batchNumber("NAL-2026-016").supplier(pharmaCorp).quantity(0).minimumStock(15).unitPrice(new BigDecimal("35.00")).manufacturingDate(LocalDate.now().minusMonths(12)).expiryDate(LocalDate.now().plusMonths(12)).storageLocation("E1-Shelf-2").status("ACTIVE").build());

                    // 17. Diazepam 5mg (Emergency) - Normal
                    medicineRepository.save(Medicine.builder().name("Diazepam 5mg").genericName("Diazepam").category(emergency).batchNumber("DIA-2026-017").supplier(mediSupply).quantity(300).minimumStock(50).unitPrice(new BigDecimal("1.80")).manufacturingDate(LocalDate.now().minusMonths(6)).expiryDate(LocalDate.now().plusMonths(18)).storageLocation("E2-Safe").status("ACTIVE").build());

                    // 18. Ibuprofen Syrup (Antipyretics) - Normal
                    medicineRepository.save(Medicine.builder().name("Ibuprofen Syrup 100mg/5ml").genericName("Ibuprofen").category(antipyretics).batchNumber("IBUS-2026-018").supplier(pharmaCorp).quantity(400).minimumStock(100).unitPrice(new BigDecimal("4.20")).manufacturingDate(LocalDate.now().minusMonths(2)).expiryDate(LocalDate.now().plusMonths(22)).storageLocation("A3-Shelf-1").status("ACTIVE").build());

                    // 19. Acetaminophen IV (Antipyretics) - Low Stock
                    medicineRepository.save(Medicine.builder().name("Acetaminophen IV 1000mg").genericName("Acetaminophen").category(antipyretics).batchNumber("ACE-2026-019").supplier(mediSupply).quantity(25).minimumStock(100).unitPrice(new BigDecimal("12.50")).manufacturingDate(LocalDate.now().minusMonths(8)).expiryDate(LocalDate.now().plusMonths(16)).storageLocation("A3-Shelf-2").status("ACTIVE").build());

                    // 20. Mefenamic Acid (Antipyretics) - Normal
                    medicineRepository.save(Medicine.builder().name("Mefenamic Acid 250mg").genericName("Mefenamic Acid").category(antipyretics).batchNumber("MEF-2026-020").supplier(pharmaCorp).quantity(800).minimumStock(200).unitPrice(new BigDecimal("0.45")).manufacturingDate(LocalDate.now().minusMonths(4)).expiryDate(LocalDate.now().plusMonths(20)).storageLocation("A3-Shelf-3").status("ACTIVE").build());
                }
            }
        };
    }
}
