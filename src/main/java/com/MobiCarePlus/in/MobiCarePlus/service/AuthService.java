package com.MobiCarePlus.in.MobiCarePlus.service;

import org.springframework.http.ResponseEntity;

import com.MobiCarePlus.in.MobiCarePlus.request.LoginRequest;
import com.MobiCarePlus.in.MobiCarePlus.request.RegisterRequest;
import com.MobiCarePlus.in.MobiCarePlus.request.ShopRegisterRequest;
import com.MobiCarePlus.in.MobiCarePlus.response.BaseResponse;
import com.MobiCarePlus.in.MobiCarePlus.response.LoginResponse;

public interface AuthService {
	  ResponseEntity<LoginResponse> login(LoginRequest request);

	ResponseEntity<BaseResponse> registerUser(RegisterRequest request);

	ResponseEntity<BaseResponse> registerShop(ShopRegisterRequest request);
}
