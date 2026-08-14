package com.example.AmarBoiPora.services;

import com.example.AmarBoiPora.dto.AddBookRequest;
import com.example.AmarBoiPora.dto.ApiResponse;
import com.example.AmarBoiPora.dto.UpdateBookRequest;
import com.example.AmarBoiPora.entity.Book;
import com.example.AmarBoiPora.entity.Users;
import com.example.AmarBoiPora.repository.BookRepository;
import com.example.AmarBoiPora.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ApiResponse addBook(AddBookRequest request, String contactNumber){
        Users vendor =userRepository.findByContactNumber(contactNumber).orElseThrow(()-> new UsernameNotFoundException("Vendor Not Found"));
        if (bookRepository.existsByIsbnAndVendorIdAndDeletedFalse(
                request.getIsbn(),
                vendor.getId())){
            return new ApiResponse(
                    false,
                    "You have already added this book."
            );

        }
        Book book = Book.builder()

                // Vendor Input
                .isbn(request.getIsbn())
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .authorName(request.getAuthorName())
                .subject(request.getSubject())
                .language(request.getLanguage())
                .bookAge(request.getBookAge())
                .publication(request.getPublication())
                .publicationYear(request.getPublicationYear())
                .price(request.getPrice())
                .stock(request.getStock())

                // System Managed
                .vendorId(vendor.getId())
                .bookPic(null)
                .unitsSold(0)
                .bestSeller(false)
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())

                .build();

        bookRepository.save(book);

        return new ApiResponse(
                true,
                "Book added successfully."
        );
    }
    public ApiResponse updateBook(String bookId,
                                  UpdateBookRequest request,
                                  String contactNumber) {

        // Logged-in Vendor
        Users vendor = userRepository.findByContactNumber(contactNumber)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Vendor not found"));

        // Check ownership
        Book book = bookRepository
                .findByIdAndVendorIdAndDeletedFalse(bookId, vendor.getId())
                .orElseThrow(() ->
                        new RuntimeException("Book not found"));

        // ==========================
        // Update Editable Fields
        // ==========================

        book.setName(request.getName());
        book.setDescription(request.getDescription());
        book.setCategory(request.getCategory());
        book.setAuthorName(request.getAuthorName());
        book.setSubject(request.getSubject());
        book.setLanguage(request.getLanguage());
        book.setBookAge(request.getBookAge());
        book.setPublication(request.getPublication());
        book.setPublicationYear(request.getPublicationYear());
        book.setPrice(request.getPrice());
        book.setStock(request.getStock());

        // ==========================
        // System Managed
        // ==========================

        book.setUpdatedAt(LocalDateTime.now());

        bookRepository.save(book);

        return new ApiResponse(
                true,
                "Book updated successfully."
        );
    }
}
