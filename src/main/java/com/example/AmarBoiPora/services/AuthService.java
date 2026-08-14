package com.example.AmarBoiPora.services;
import com.example.AmarBoiPora.dto.ApiResponse;
import com.example.AmarBoiPora.dto.LoginRequest;
import com.example.AmarBoiPora.dto.LoginResponse;
import com.example.AmarBoiPora.dto.RegisterRequest;
import com.example.AmarBoiPora.entity.Users;
import com.example.AmarBoiPora.enums.Role;
import com.example.AmarBoiPora.enums.VendorStatus;
import com.example.AmarBoiPora.repository.UserRepository;
import com.example.AmarBoiPora.security.JwtService;
import lombok.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
      private final JwtService jwtService;

      public AuthService(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.jwtService = jwtService;
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
      }
      private final UserRepository userRepository;
      private final PasswordEncoder passwordEncoder;


      public ApiResponse register(RegisterRequest request) {
            if (userRepository.existsByContactNumber(request.getContactNumber())) {
                  return new ApiResponse(false, "Contact number already exists.");
            }
            if (userRepository.existsByEmail(request.getEmail())) {
                  return new ApiResponse(false, "Email ID already exists.");
            }
            if (!request.getPassword().equals(request.getConfirmPassword())) {
                  return new ApiResponse(false, "Password doesn't match");
            }
            if (request.getRole() == Role.ADMIN) {
                  return new ApiResponse(false, "Invalid Role");
            }
            Users user = Users.builder()
                    .name(request.getName())
                    .shopName(request.getShopName())
                    .contactNumber(request.getContactNumber())
                    .whatsappNumber(request.getWhatsappNumber())
                    .email(request.getEmail())
                    .address(request.getAddress())
                    .aadhaarNumber(request.getAadhaarNumber())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .status(request.getRole() == Role.VENDOR ? VendorStatus.PENDING : null)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            userRepository.save(user);
            return new ApiResponse(true,"Successful");
      }
      public LoginResponse login (LoginRequest request){
            Users users =userRepository.findByContactNumber(request.getContactNumber())
                    .orElseThrow(()-> new UsernameNotFoundException("User not exists"));
            if (!passwordEncoder.matches(request.getPassword(), users.getPassword())){
                  throw  new RuntimeException("Invalid Password babes not able to login to this hole");
            }
            System.out.println(passwordEncoder.matches("password123", users.getPassword()));
            String token = jwtService.generateToken(users);
            return new LoginResponse(
                    token,
                    users.getName(),
                    users.getRole().name(),
                    "Login Successful"
            );
      }
      


}