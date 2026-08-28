package com.example.AmarBoiPora.services;

import com.example.AmarBoiPora.dto.VendorDashboardResponse;
import com.example.AmarBoiPora.entity.Book;
import com.example.AmarBoiPora.entity.Users;
import com.example.AmarBoiPora.enums.Role;
import com.example.AmarBoiPora.repository.BookRepository;
import com.example.AmarBoiPora.repository.UserRepository;
import lombok.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorDashboardService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public VendorDashboardResponse getDashboard(
            String contactNumber) {

        Users vendor = userRepository
                .findByContactNumber(contactNumber)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Vendor not found"
                        ));

        if (vendor.getRole() != Role.VENDOR) {
            throw new RuntimeException(
                    "User is not a vendor"
            );
        }

        String vendorId = vendor.getId();

        long totalBooks =
                bookRepository.countByVendorId(vendorId);

        long activeBooks =
                bookRepository.countByVendorIdAndDeletedFalse(
                        vendorId
                );

        long bestSellerBooks =
                bookRepository
                        .countByVendorIdAndBestSellerTrueAndDeletedFalse(
                                vendorId
                        );

        List<Book> activeBookList =
                bookRepository
                        .findAllByVendorIdAndDeletedFalse(vendorId);

        long totalStock = activeBookList.stream()
                .mapToLong(book ->
                        book.getStock() != null
                                ? book.getStock()
                                : 0
                )
                .sum();

        long totalUnitsSold = activeBookList.stream()
                .mapToLong(book ->
                        book.getUnitsSold() != null
                                ? book.getUnitsSold()
                                : 0
                )
                .sum();

        double estimatedSales = activeBookList.stream()
                .mapToDouble(book -> {

                    double price =
                            book.getPrice() != null
                                    ? book.getPrice()
                                    : 0;

                    int units =
                            book.getUnitsSold() != null
                                    ? book.getUnitsSold()
                                    : 0;

                    return price * units;
                })
                .sum();

        return VendorDashboardResponse.builder()
                .vendorName(vendor.getName())
                .shopName(vendor.getShopName())
                .status(
                        vendor.getStatus() != null
                                ? vendor.getStatus().name()
                                : null
                )
                .totalBooks(totalBooks)
                .activeBooks(activeBooks)
                .totalStock(totalStock)
                .totalUnitsSold(totalUnitsSold)
                .bestSellerBooks(bestSellerBooks)
                .estimatedSales(estimatedSales)
                .build();
    }
}