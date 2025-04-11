package com.MobiCarePlus.in.MobiCarePlus.service;

import org.springframework.http.ResponseEntity;

import com.MobiCarePlus.in.MobiCarePlus.request.AddProductOwner;
import com.MobiCarePlus.in.MobiCarePlus.response.BaseResponse;

public interface IProductOwnerService {

	ResponseEntity<BaseResponse> addProductOwner(AddProductOwner request);

	ResponseEntity<?> getProductOwnerByUserId(Long userId);

	ResponseEntity<?> getAllShops();

}
