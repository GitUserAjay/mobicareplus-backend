package com.MobiCarePlus.in.MobiCarePlus.service;

import org.springframework.http.ResponseEntity;

import com.MobiCarePlus.in.MobiCarePlus.request.PlaceOrderRequest;
import com.MobiCarePlus.in.MobiCarePlus.response.BaseResponse;

public interface IOrderService {
	ResponseEntity<BaseResponse> placeOrder(PlaceOrderRequest request);
}
