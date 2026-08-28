package com.example.AmarBoiPora.controllers;

import com.example.AmarBoiPora.dto.VendorDashboardResponse;
import com.example.AmarBoiPora.services.VendorDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor/dashboard")
@RequiredArgsConstructor
public class VendorDashboardController {

    private final VendorDashboardService vendorDashboardService;

    @GetMapping
    public ResponseEntity<VendorDashboardResponse> getDashboard(
            Authentication authentication) {

        return ResponseEntity.ok(
                vendorDashboardService.getDashboard(
                        authentication.getName()
                )
        );
    }
}