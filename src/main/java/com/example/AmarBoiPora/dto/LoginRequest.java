package com.example.AmarBoiPora.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    private String contactNumber;

    @NotBlank
    private String password;
}
