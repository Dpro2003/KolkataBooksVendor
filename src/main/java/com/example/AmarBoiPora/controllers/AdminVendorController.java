package com.example.AmarBoiPora.controllers;

import com.example.AmarBoiPora.dto.ApiResponse;
import com.example.AmarBoiPora.entity.Users;
import com.example.AmarBoiPora.enums.VendorStatus;
import com.example.AmarBoiPora.services.AdminVendorService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/vendors")
@RequiredArgsConstructor
public class AdminVendorController {

    private final AdminVendorService adminVendorService;

    @PatchMapping("/{vendorId}/status")
    public ResponseEntity<ApiResponse> updateVendorStatus(
            @PathVariable String vendorId,
            @RequestParam VendorStatus status) {

        return ResponseEntity.ok(
                adminVendorService.updateVendorStatus(vendorId, status)
        );
    }

    @GetMapping
    public ResponseEntity<List<Users>> getVendors(@RequestParam VendorStatus status) {
        return ResponseEntity.ok(adminVendorService.getVendorsByStatus(status));
    }
}
