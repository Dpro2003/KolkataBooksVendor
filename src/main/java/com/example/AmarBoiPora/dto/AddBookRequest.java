package com.example.AmarBoiPora.dto;
import com.example.AmarBoiPora.enums.BookCondition;
import lombok.Data;

@Data
public class AddBookRequest    {
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


}
