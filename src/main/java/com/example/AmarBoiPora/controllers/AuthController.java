package com.example.AmarBoiPora.controllers;


import com.example.AmarBoiPora.dto.ApiResponse;
import com.example.AmarBoiPora.dto.LoginRequest;
import com.example.AmarBoiPora.dto.LoginResponse;
import com.example.AmarBoiPora.dto.RegisterRequest;
import com.example.AmarBoiPora.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request){
        ApiResponse response = authService.register(request);
        if (response.isSuccess()){
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }
        return ResponseEntity.badRequest().body(response);
    }
    @PostMapping("/Login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){

        return ResponseEntity.ok(authService.login(request));
    }

}
