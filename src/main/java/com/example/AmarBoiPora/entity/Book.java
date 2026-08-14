package com.example.AmarBoiPora.entity;
import com.example.AmarBoiPora.enums.BookCondition;
import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.index.IndexOptions;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "books")
public class Book {
    @Id
    private String id;

    private String isbn;

    private String name;

    private String description;

    private BookCondition category;

    private String authorName;

    private String subject;

    private String language;

    private String bookAge;

    private String publication;

    private String publicationYear;

    private Double price;

    private Integer stock;

    private String bookPic;      // Single image

    @Indexed
    private String vendorId;

    private Integer unitsSold = 0;

    private boolean bestSeller = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean deleted; // for admin soft delete
}
