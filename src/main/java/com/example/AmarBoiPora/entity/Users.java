package com.example.AmarBoiPora.entity;
import java.time.LocalDateTime;

import com.example.AmarBoiPora.enums.Role;
import com.example.AmarBoiPora.enums.VendorStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexOptions;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    private String id;

    private String name;

    private String shopName;

    @Indexed(unique = true)
    private String contactNumber;

    private String whatsappNumber;

    @Indexed(unique = true)
    private String email;

    private String address;

    @Indexed(unique = true)
    private String aadhaarNumber;

    private String password;

    private Role role;

    private VendorStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
