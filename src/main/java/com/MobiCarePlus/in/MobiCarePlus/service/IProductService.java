package com.MobiCarePlus.in.MobiCarePlus.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.MobiCarePlus.in.MobiCarePlus.entity.Product;
import com.MobiCarePlus.in.MobiCarePlus.request.ProductRequest;
import com.MobiCarePlus.in.MobiCarePlus.response.BaseResponse;

public interface IProductService {
    ResponseEntity<BaseResponse> addProduct(ProductRequest product);

	ResponseEntity<List<Product>> getAllProductByUserId(Long id);

	ResponseEntity<Product> getProductById(Long id);

	ResponseEntity<BaseResponse> updateProduct(Long id, ProductRequest request);

	ResponseEntity<BaseResponse> deleteProduct(Long id);

	ResponseEntity<List<Product>> getAllProducts();
   
}
