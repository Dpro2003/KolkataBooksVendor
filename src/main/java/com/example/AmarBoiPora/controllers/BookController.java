package com.example.AmarBoiPora.controllers;


import com.example.AmarBoiPora.dto.AddBookRequest;
import com.example.AmarBoiPora.dto.ApiResponse;
import com.example.AmarBoiPora.dto.UpdateBookRequest;
import com.example.AmarBoiPora.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;



    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addBook(
            @RequestBody AddBookRequest request,
            Authentication authentication) {

        String contactNumber = authentication.getName();

        return ResponseEntity.ok(
                bookService.addBook(request, contactNumber)
        );
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<ApiResponse> updateBook(
            @PathVariable String bookId,
            @RequestBody UpdateBookRequest request,
            Authentication authentication) {

        ApiResponse response = bookService.updateBook(
                bookId,
                request,
                authentication.getName()
        );

        return ResponseEntity.ok(response);
    }


}
