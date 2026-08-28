package com.example.AmarBoiPora.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorDashboardResponse {

    private String vendorName;

    private String shopName;

    private String status;

    private long totalBooks;

    private long activeBooks;

    private long totalStock;

    private long totalUnitsSold;

    private long bestSellerBooks;

    private double estimatedSales;
}
