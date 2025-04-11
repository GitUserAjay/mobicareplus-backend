package com.MobiCarePlus.in.MobiCarePlus.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MobiCarePlus.in.MobiCarePlus.entity.Product;
import com.MobiCarePlus.in.MobiCarePlus.entity.ShopUser;
import com.MobiCarePlus.in.MobiCarePlus.entity.UserInfo;
import com.MobiCarePlus.in.MobiCarePlus.repository.ProductReposistory;
import com.MobiCarePlus.in.MobiCarePlus.repository.ShopUserRepository;
import com.MobiCarePlus.in.MobiCarePlus.repository.UserInfoRepository;
import com.MobiCarePlus.in.MobiCarePlus.request.ProductRequest;
import com.MobiCarePlus.in.MobiCarePlus.response.BaseResponse;
import com.MobiCarePlus.in.MobiCarePlus.service.IProductService;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements IProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductReposistory productReposistory;
	@Autowired
	private UserInfoRepository infoRepository;
	@Autowired
	private ShopUserRepository shopUserRepository;

	@Override
	@Transactional
	public ResponseEntity<BaseResponse> addProduct(ProductRequest request) {
	    logger.info("Service: ProductServiceImpl | Method: addProduct | Params: {}", request);

	    try {
	        // Validation
	        if (request.getName() == null || request.getName().isEmpty()) {
	            throw new IllegalArgumentException("Product name is required");
	        }
	       
	        if (request.getUserId() == null) {
	            throw new IllegalArgumentException("User ID is required");
	        }
	       UserInfo byId = infoRepository.findById(request.getUserId()).get();
	        // Step 1: Validate and get ShopUser by userId
	        ShopUser shopUser = shopUserRepository.findByUserInfo(byId);
	               
	        // Step 2: Create Product entity and populate fields
	        Product product = new Product();
	        product.setName(request.getName());
	        product.setCategory(request.getCategory());
	        product.setPrice(request.getPrice());
	        product.setImage(request.getImage() != null ? request.getImage() : null); // Handle null image
	        product.setShopUser(shopUser);
	        product.setStatus(request.getStatus());
	        product.setCreatedAt(LocalDateTime.now());
	        product.setUpdatedAt(LocalDateTime.now());

	        // Step 3: Save the product
	        productReposistory.save(product);

	        // Step 4: Log success and return response
	        logger.info("Product added successfully with ID: {}", product.getId());
	        BaseResponse response = new BaseResponse("Product added successfully!", "1");
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    } catch (IllegalArgumentException e) {
	        logger.error("Validation error: ", e);
	        BaseResponse errorResponse = new BaseResponse(e.getMessage(), "0");
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	    } catch (RuntimeException e) {
	        logger.error("User-related error: ", e);
	        BaseResponse errorResponse = new BaseResponse("Shop user not found: " + e.getMessage(), "0");
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        logger.error("Unexpected error: ", e);
	        BaseResponse errorResponse = new BaseResponse("Failed to add product: " + e.getMessage(), "0");
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	@Override
	public ResponseEntity<List<Product>> getAllProductByUserId(Long id) {
		  UserInfo byId = infoRepository.findById(id).get();
		ShopUser shopUser = shopUserRepository.findByUserInfo(byId);
			
		List<Product> products = productReposistory.findAllByShopUser(shopUser);
		return ResponseEntity.ok(products);
	}

	@Override
	public ResponseEntity<Product> getProductById(Long id) {
		Optional<Product> product = productReposistory.findById(id);
		return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@Override
	@Transactional
	public ResponseEntity<BaseResponse> updateProduct(Long id, ProductRequest request) {
		logger.info(
				"Controller: ProductController | Service: ProductServiceImpl | Method: updateProduct | Product ID: {}",
				id);

		try {
			Product product = productReposistory.findById(id)
					.orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

			// Update product fields
			product.setName(request.getName());
			product.setCategory(request.getCategory());
			product.setPrice(request.getPrice());
			product.setImage(request.getImage());
			product.setStatus(request.getStatus());
			product.setUpdatedAt(LocalDateTime.now());

			productReposistory.save(product);
			return ResponseEntity.ok(new BaseResponse("Product updated successfully!", "1"));
		} catch (Exception e) {
			logger.error("Error occurred while updating product: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new BaseResponse("Failed to update product: " + e.getMessage(), "0"));
		}
	}

	@Override
	@Transactional
	public ResponseEntity<BaseResponse> deleteProduct(Long id) {
		logger.info(
				"Controller: ProductController | Service: ProductServiceImpl | Method: deleteProduct | Product ID: {}",
				id);

		try {
			if (!productReposistory.existsById(id)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new BaseResponse("Product not found with ID: " + id, "0"));
			}

			productReposistory.deleteById(id);
			return ResponseEntity.ok(new BaseResponse("Product deleted successfully!", "1"));
		} catch (Exception e) {
			logger.error("Error occurred while deleting product: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new BaseResponse("Failed to delete product: " + e.getMessage(), "0"));
		}
	}
	@Override
	public ResponseEntity<List<Product>> getAllProducts() {
		// TODO Auto-generated method stub
		List<Product> all = productReposistory.findAll();
		return new ResponseEntity<>(all,HttpStatus.OK);
	}

}
