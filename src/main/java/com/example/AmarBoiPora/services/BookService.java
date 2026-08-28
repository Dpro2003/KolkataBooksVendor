package com.example.AmarBoiPora.services;

import com.example.AmarBoiPora.dto.AddBookRequest;
import com.example.AmarBoiPora.dto.ApiResponse;
import com.example.AmarBoiPora.dto.UpdateBookRequest;
import com.example.AmarBoiPora.dto.UpdateBookPicRequest;
import com.example.AmarBoiPora.entity.Book;
import com.example.AmarBoiPora.entity.Users;
import com.example.AmarBoiPora.repository.BookRepository;
import com.example.AmarBoiPora.repository.UserRepository;
import lombok.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<Book> getMyBooks(String contactNumber) {
        Users vendor = userRepository.findByContactNumber(contactNumber)
                .orElseThrow(() -> new UsernameNotFoundException("Vendor Not Found"));
        
        return bookRepository.findAllByVendorIdAndDeletedFalse(vendor.getId());
    }

    public List<Book> getBooksByVendor(String vendorId) {
        return bookRepository.findAllByVendorIdAndDeletedFalse(vendorId);
    }

    public ApiResponse updateBookPic(String bookId, UpdateBookPicRequest request){
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new RuntimeException("book not found"));
        if (book.isDeleted()){
            return new ApiResponse(
                    false,
                    "Cannot Update image of a deleted book not allowed"
            );

        }
        book.setBookPic(request.getBookPic());
        book.setUpdatedAt(LocalDateTime.now());

        bookRepository.save(book);

        return new ApiResponse(
                true,
                "Book image updated successfully."
        );
    }

    public ApiResponse toggleBestSeller(String bookId, boolean status) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
        book.setBestSeller(status);
        bookRepository.save(book);
        
        return new ApiResponse(true, "Book best seller status updated to " + status);
    }

    public ApiResponse softDeleteBook(String bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
        book.setDeleted(true);
        bookRepository.save(book);
        
        return new ApiResponse(true, "Book has been deleted successfully");
    }
}
