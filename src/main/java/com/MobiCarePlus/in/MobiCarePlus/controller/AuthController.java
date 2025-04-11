package com.MobiCarePlus.in.MobiCarePlus.controller;

import com.MobiCarePlus.in.MobiCarePlus.request.LoginRequest;
import com.MobiCarePlus.in.MobiCarePlus.request.RegisterRequest;
import com.MobiCarePlus.in.MobiCarePlus.request.ShopRegisterRequest;
import com.MobiCarePlus.in.MobiCarePlus.response.BaseResponse;
import com.MobiCarePlus.in.MobiCarePlus.response.LoginResponse;
import com.MobiCarePlus.in.MobiCarePlus.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
    
    @PostMapping("/register")
    public ResponseEntity<BaseResponse> registerUser(@RequestBody RegisterRequest request) {
        return authService.registerUser(request);
    }
    @PostMapping("/shop")
    public ResponseEntity<BaseResponse> registerShop(@RequestBody ShopRegisterRequest request) {
        return authService.registerShop(request);
    }
}
