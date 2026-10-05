package com.medicinetracker.service;

import com.medicinetracker.dto.RegistrationDto;
import com.medicinetracker.entity.AccessKey;
import com.medicinetracker.entity.User;
import com.medicinetracker.repository.AccessKeyRepository;
import com.medicinetracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AccessKeyRepository accessKeyRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, AccessKeyRepository accessKeyRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accessKeyRepository = accessKeyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(RegistrationDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        AccessKey key = accessKeyRepository.findByKey(dto.getAccessKey())
                .orElseThrow(() -> new IllegalArgumentException("Invalid access key"));

        if (!"ACTIVE".equals(key.getStatus())) {
            throw new IllegalArgumentException("Access key is no longer active");
        }

        User user = User.builder()
                .fullName(dto.getFullName())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .active(true)
                .build();

        user = userRepository.save(user);

        // key.setStatus("USED");
        // key.setUsedBy(user);
        // accessKeyRepository.save(key);
    }
}
