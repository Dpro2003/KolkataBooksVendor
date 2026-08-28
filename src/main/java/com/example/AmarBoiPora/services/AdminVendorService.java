package com.example.AmarBoiPora.services;

import com.example.AmarBoiPora.dto.ApiResponse;
import com.example.AmarBoiPora.entity.Users;
import com.example.AmarBoiPora.enums.Role;
import com.example.AmarBoiPora.enums.VendorStatus;
import com.example.AmarBoiPora.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminVendorService {

    private final UserRepository userRepository;

    public ApiResponse updateVendorStatus(String vendorId, VendorStatus status) {
        Users vendor = userRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendor.setStatus(status);
        userRepository.save(vendor);

        return new ApiResponse(true, "Vendor status updated to " + status.name());
    }

    public List<Users> getVendorsByStatus(VendorStatus status) {
        return userRepository.findAllByRoleAndStatus(Role.VENDOR, status);
    }
}
