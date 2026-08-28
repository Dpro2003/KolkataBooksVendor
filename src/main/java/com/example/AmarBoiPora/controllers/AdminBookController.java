package com.example.AmarBoiPora.controllers;

import com.example.AmarBoiPora.dto.ApiResponse;
import com.example.AmarBoiPora.dto.UpdateBookPicRequest;
import com.example.AmarBoiPora.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.AmarBoiPora.entity.Book;

@RestController
@RequestMapping("/api/admin/books")
@RequiredArgsConstructor
public class AdminBookController {

    private final BookService bookService;

    @PatchMapping("/{bookId}/thumbnail")
    public ResponseEntity<ApiResponse> updateBookPic(
            @PathVariable String bookId,
            @RequestBody UpdateBookPicRequest request) {

        return ResponseEntity.ok(
                bookService.updateBookPic(
                        bookId,
                        request
                )
        );
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<Book>> getBooksByVendor(@PathVariable String vendorId) {
        return ResponseEntity.ok(bookService.getBooksByVendor(vendorId));
    }

    @PatchMapping("/{bookId}/bestseller")
    public ResponseEntity<ApiResponse> toggleBestSeller(
            @PathVariable String bookId,
            @RequestParam boolean status) {
        return ResponseEntity.ok(bookService.toggleBestSeller(bookId, status));
    }

    @PatchMapping("/{bookId}/delete")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable String bookId) {
        return ResponseEntity.ok(bookService.softDeleteBook(bookId));
    }
}