package com.MobiCarePlus.in.MobiCarePlus.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.MobiCarePlus.in.MobiCarePlus.entity.ShopService;
import com.MobiCarePlus.in.MobiCarePlus.request.ServiceRequest;
import com.MobiCarePlus.in.MobiCarePlus.request.UpdateShopProfileRequest;
import com.MobiCarePlus.in.MobiCarePlus.request.UpdateShopServiceRequest;
import com.MobiCarePlus.in.MobiCarePlus.service.IShopService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/shop")
public class ShopController {
	@Autowired
	private IShopService shopService;

	@GetMapping("/{userId}")
	public ResponseEntity<?> getShopDetails(@PathVariable Long userId) {
		return shopService.getShopDetails(userId);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<?> updateShopProfile(@RequestBody UpdateShopProfileRequest request) {
		return shopService.updateShopProfile(request);
	}

	@PostMapping("/add-service")
	public ResponseEntity<?> addService(@RequestBody ServiceRequest request) {
		return shopService.addService(request);
	}

	@GetMapping("/get-all-shop-services-by-id/{userId}")
	public ResponseEntity<?> getAllShopServices(@PathVariable Long userId) {
		return shopService.getAllShopServices(userId);
	}

	// New API to get a service by ID for a specific user
	@GetMapping("/service/{userId}/{serviceId}")
	public ResponseEntity<?> getServiceById(@PathVariable Long userId, @PathVariable Long serviceId) {
		return shopService.getServiceById(userId, serviceId);
	}

	@PutMapping("/service/{userId}/{serviceId}")
	public ResponseEntity<?> updateShopService(@PathVariable Long userId, @PathVariable Long serviceId,
			@RequestBody UpdateShopServiceRequest request) {
		return shopService.updateShopService(userId, serviceId, request);
	}

	@DeleteMapping("/service/{userId}/{serviceId}")
	public ResponseEntity<?> deleteShopService(@PathVariable Long userId, @PathVariable Long serviceId) {
		return shopService.deleteShopService(userId, serviceId);
	}

	@PutMapping("/update-status/{shopId}")
    public ResponseEntity<?> updateShopStatus(
            @PathVariable("shopId") Long shopId,
            @RequestBody Map<String, Boolean> request) {
        try {
            Boolean isActive = request.get("isActive");
            if (isActive == null) {
                return new ResponseEntity<>(
                    new RuntimeException("isActive field is required in request body"),
                    HttpStatus.BAD_REQUEST
                );
            }
            return shopService.updateShopStatus(shopId, isActive);
        } catch (Exception e) {
            return new ResponseEntity<>(
                new RuntimeException("An unexpected error occurred: " + e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

	@GetMapping("/all-shops")
	public ResponseEntity<?> getAllShops() {
		System.out.println("get all shops called");
		return shopService.getAllShops();
	}

	@GetMapping("/get-shop-data/{shopId}")
	public ResponseEntity<?> getShopDetailByShopId(@PathVariable Long shopId) {
		System.out.println("getShopDetailByShopId");
		return shopService.getShopDetailByShopId(shopId);
	}

	@GetMapping("/services/{shopId}")
	public ResponseEntity<List<ShopService>> getServicesByShopId(@PathVariable Long shopId) {
		List<ShopService> services = shopService.getServicesByShopId(shopId);
		return new ResponseEntity<>(services, HttpStatus.OK);
	}
	@GetMapping("/all-service")
	public ResponseEntity<?> getAllService() {
		return shopService.getAllService();
	}

}
