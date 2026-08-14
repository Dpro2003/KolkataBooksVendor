package com.example.AmarBoiPora.dto;

import com.example.AmarBoiPora.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    private String name;
    private String shopName;

    private String contactNumber;

    private String whatsappNumber;

    private String email;

    private String address;

    private String aadhaarNumber;

    private String password;

    private String confirmPassword;

    private Role role;

}