package com.MobiCarePlus.in.MobiCarePlus.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MobiCarePlus.in.MobiCarePlus.entity.Product;
import com.MobiCarePlus.in.MobiCarePlus.request.ProductRequest;
import com.MobiCarePlus.in.MobiCarePlus.response.BaseResponse;
import com.MobiCarePlus.in.MobiCarePlus.service.IProductService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    // Add a new product
    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addProduct(@RequestBody ProductRequest request) {
        return productService.addProduct(request);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
    @GetMapping("get-all-product/{id}")
    public ResponseEntity<List<Product>> getAllProductShopId(@PathVariable Long id) {
        return productService.getAllProductByUserId(id);
    }
    // Update product
    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    // Delete product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
    @GetMapping("products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return productService.getAllProducts();
    }
}
