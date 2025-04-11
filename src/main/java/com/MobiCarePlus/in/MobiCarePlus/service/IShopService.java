package com.MobiCarePlus.in.MobiCarePlus.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.MobiCarePlus.in.MobiCarePlus.entity.ShopService;
import com.MobiCarePlus.in.MobiCarePlus.request.ServiceRequest;
import com.MobiCarePlus.in.MobiCarePlus.request.UpdateShopProfileRequest;
import com.MobiCarePlus.in.MobiCarePlus.request.UpdateShopServiceRequest;

public interface IShopService {

	ResponseEntity<?> getShopDetails(Long userId);

	ResponseEntity<?> updateShopProfile(UpdateShopProfileRequest request);

	ResponseEntity<?> addService(ServiceRequest request);

	ResponseEntity<?> getAllShopServices(Long userId);

	ResponseEntity<?> getServiceById(Long userId, Long serviceId);

	ResponseEntity<?> updateShopService(Long userId, Long serviceId, UpdateShopServiceRequest request);

	ResponseEntity<?> deleteShopService(Long userId, Long serviceId);

	ResponseEntity<?> updateShopStatus(Long userId, boolean isActive);

	ResponseEntity<?> getAllShops();

	ResponseEntity<?> getShopDetailByShopId(Long shopId);

	List<ShopService> getServicesByShopId(Long shopId);

	ResponseEntity<?> getAllService();

}
