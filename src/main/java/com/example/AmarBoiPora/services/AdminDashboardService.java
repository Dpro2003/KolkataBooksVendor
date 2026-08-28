package com.example.AmarBoiPora.services;

import com.example.AmarBoiPora.dto.AdminDashboardResponse;
import com.example.AmarBoiPora.enums.Role;
import com.example.AmarBoiPora.enums.VendorStatus;
import com.example.AmarBoiPora.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {
    private final UserRepository userRepository;

    public AdminDashboardResponse getDashboardData() {
        long approved = userRepository.countByRoleAndStatus(Role.VENDOR, VendorStatus.APPROVED);
        long pending = userRepository.countByRoleAndStatus(Role.VENDOR, VendorStatus.PENDING);

        return AdminDashboardResponse.builder()
                .approvedVendors(approved)
                .pendingVendors(pending)
                .build();
    }
}
