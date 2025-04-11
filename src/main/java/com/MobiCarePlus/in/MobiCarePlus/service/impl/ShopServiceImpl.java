package com.MobiCarePlus.in.MobiCarePlus.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MobiCarePlus.in.MobiCarePlus.entity.ShopService;
import com.MobiCarePlus.in.MobiCarePlus.entity.ShopUser;
import com.MobiCarePlus.in.MobiCarePlus.entity.UserInfo;
import com.MobiCarePlus.in.MobiCarePlus.repository.ShopServiceRepository;
import com.MobiCarePlus.in.MobiCarePlus.repository.ShopUserRepository;
import com.MobiCarePlus.in.MobiCarePlus.repository.UserInfoRepository;
import com.MobiCarePlus.in.MobiCarePlus.request.ServiceRequest;
import com.MobiCarePlus.in.MobiCarePlus.request.UpdateShopProfileRequest;
import com.MobiCarePlus.in.MobiCarePlus.request.UpdateShopServiceRequest;
import com.MobiCarePlus.in.MobiCarePlus.service.IShopService;

import jakarta.transaction.Transactional;

@Service
public class ShopServiceImpl implements IShopService {
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private ShopServiceRepository shopServiceRepository;
	@Autowired
	private ShopUserRepository shopUserRepository;

	@Override
	public ResponseEntity<?> getShopDetails(Long userId) {
		UserInfo userInfo = userInfoRepository.findById(userId).get();
		if (userInfo != null) {
			ShopUser shopUser = shopUserRepository.findByUserInfo(userInfo);

			return new ResponseEntity<>(shopUser, HttpStatus.OK);
		}
		return null;
	}

	@Override
	public ResponseEntity<?> updateShopProfile(UpdateShopProfileRequest request) {
		// 1️ Find the user by ID
		Optional<UserInfo> userInfoOptional = userInfoRepository.findById(request.getUserId());
		if (!userInfoOptional.isPresent()) {
			return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
		}
		UserInfo userInfo = userInfoOptional.get();

		// 2️ Find the shop associated with the user
		ShopUser shopUser = shopUserRepository.findByUserInfo(userInfo);
		if (shopUser == null) {
			return new ResponseEntity<>("Shop not found!", HttpStatus.NOT_FOUND);
		}

		// 3️⃣ Update shop details
		shopUser.setShopName(request.getShopName());
		shopUser.setOwnerName(request.getOwnerName());
		shopUser.setBusinessType(request.getBusinessType());
		shopUser.setGstNumber(request.getGstNumber());
		shopUser.setPanNumber(request.getPanNumber());
		shopUser.setImage(request.getShopLogoUrl());
		shopUser.setWebsiteUrl(request.getWebsiteUrl());

		// 4️⃣ Save the updated shop details
		shopUserRepository.save(shopUser);

		return new ResponseEntity<>(shopUser, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> addService(ServiceRequest request) {
		// 1️⃣ Find the user by ID
		Optional<UserInfo> userInfoOptional = userInfoRepository.findById(request.getUserId());
		if (!userInfoOptional.isPresent()) {
			return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
		}
		UserInfo userInfo = userInfoOptional.get();

		// 2️⃣ Find the shop associated with the user
		ShopUser shopUser = shopUserRepository.findByUserInfo(userInfo);
		if (shopUser == null) {
			return new ResponseEntity<>("Shop not found for this user!", HttpStatus.NOT_FOUND);
		}

		// 3️⃣ Create a new ShopService entity
		ShopService service = new ShopService();
		service.setName(request.getName());
		service.setCategory(request.getCategory());
		service.setDescription(request.getDescription());
		service.setPrice(request.getPrice());
		service.setDiscount(request.getDiscount());
		service.setEstimatedTime(request.getEstimatedTime());
		service.setContact(request.getContact());
		service.setLocation(request.getLocation());
		service.setAvailable(request.getAvailable());
		service.setImageBase64(request.getImageBase64());
		service.setImageName(request.getImageName());

		// 4️⃣ Save the service associated with the shop
		service.setShopUser(shopUser); // Associate service with ShopUser
		shopServiceRepository.save(service);

		return new ResponseEntity<>(service, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> getAllShopServices(Long userId) {
		// Find the user by ID
		Optional<UserInfo> userInfoOptional = userInfoRepository.findById(userId);
		if (!userInfoOptional.isPresent()) {
			return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
		}

		UserInfo userInfo = userInfoOptional.get();

		// Find the shop associated with the user
		ShopUser shopUser = shopUserRepository.findByUserInfo(userInfo);
		if (shopUser == null) {
			return new ResponseEntity<>("Shop not found for this user!", HttpStatus.NOT_FOUND);
		}

		// Fetch all services associated with the shop
		List<ShopService> services = shopServiceRepository.findAllByShopUser(shopUser);
		if (services.isEmpty()) {
			return new ResponseEntity<>("No services found for this shop!", HttpStatus.NOT_FOUND);
		}

		// Return the list of services
		return new ResponseEntity<>(services, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getServiceById(Long userId, Long serviceId) {
		// 1️⃣ Find the user by ID
		Optional<UserInfo> userInfoOptional = userInfoRepository.findById(userId);
		if (!userInfoOptional.isPresent()) {
			return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
		}
		UserInfo userInfo = userInfoOptional.get();

		// 2️⃣ Find the shop associated with the user
		ShopUser shopUser = shopUserRepository.findByUserInfo(userInfo);
		if (shopUser == null) {
			return new ResponseEntity<>("Shop not found for this user!", HttpStatus.NOT_FOUND);
		}

		// 3️⃣ Fetch the service by its ID associated with the shop
		Optional<ShopService> serviceOptional = shopServiceRepository.findById(serviceId);
		if (!serviceOptional.isPresent() || !serviceOptional.get().getShopUser().equals(shopUser)) {
			return new ResponseEntity<>("Service not found for this shop!", HttpStatus.NOT_FOUND);
		}

		// 4️⃣ Return the service
		return new ResponseEntity<>(serviceOptional.get(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateShopService(Long userId, Long serviceId, UpdateShopServiceRequest request) {
		// 1️⃣ Find the user by ID
		Optional<UserInfo> userInfoOptional = userInfoRepository.findById(userId);
		if (!userInfoOptional.isPresent()) {
			return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
		}
		UserInfo userInfo = userInfoOptional.get();

		// 2️⃣ Find the shop associated with the user
		ShopUser shopUser = shopUserRepository.findByUserInfo(userInfo);
		if (shopUser == null) {
			return new ResponseEntity<>("Shop not found for this user!", HttpStatus.NOT_FOUND);
		}

		// 3️⃣ Fetch the service by its ID
		Optional<ShopService> serviceOptional = shopServiceRepository.findById(serviceId);
		if (!serviceOptional.isPresent() || !serviceOptional.get().getShopUser().equals(shopUser)) {
			return new ResponseEntity<>("Service not found for this shop!", HttpStatus.NOT_FOUND);
		}

		ShopService service = serviceOptional.get();

		// 4️⃣ Update the service details
		service.setName(request.getName());
		service.setCategory(request.getCategory());
		service.setDescription(request.getDescription());
		service.setPrice(request.getPrice());
		service.setDiscount(request.getDiscount());
		service.setEstimatedTime(request.getEstimatedTime());
		service.setContact(request.getContact());
		service.setLocation(request.getLocation());
		service.setAvailable(request.getAvailable());
		service.setImageBase64(request.getImageBase64());
		service.setImageName(request.getImageName());

		// 5️⃣ Save the updated service
		shopServiceRepository.save(service);

		// 6️⃣ Return the updated service
		return new ResponseEntity<>(service, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteShopService(Long userId, Long serviceId) {
		// 1️⃣ Find the user by ID
		Optional<UserInfo> userInfoOptional = userInfoRepository.findById(userId);
		if (!userInfoOptional.isPresent()) {
			return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
		}
		UserInfo userInfo = userInfoOptional.get();

		// 2️⃣ Find the shop associated with the user
		ShopUser shopUser = shopUserRepository.findByUserInfo(userInfo);
		if (shopUser == null) {
			return new ResponseEntity<>("Shop not found for this user!", HttpStatus.NOT_FOUND);
		}

		// 3️⃣ Fetch the service by its ID
		Optional<ShopService> serviceOptional = shopServiceRepository.findById(serviceId);
		if (!serviceOptional.isPresent() || !serviceOptional.get().getShopUser().equals(shopUser)) {
			return new ResponseEntity<>("Service not found for this shop!", HttpStatus.NOT_FOUND);
		}

		// 4️⃣ Delete the service
		ShopService service = serviceOptional.get();
		shopServiceRepository.delete(service);

		// 5️⃣ Return success response
		return new ResponseEntity<>("Service deleted successfully!", HttpStatus.OK);
	}

	@Override
    @Transactional
    public ResponseEntity<?> updateShopStatus(Long shopId, boolean isActive) {
        Optional<ShopUser> shopUserOptional = shopUserRepository.findById(shopId);
      

        ShopUser shopUser = shopUserOptional.get();
        

        shopUser.setActive(isActive);
        shopUser.setUpdatedAt(LocalDateTime.now());
        ShopUser updatedShop = shopUserRepository.save(shopUser);

        // Return a simple success message that matches your frontend expectation
        return ResponseEntity.ok(
            Map.of(
                "message", "Shop " + (isActive ? "activated" : "deactivated") + " successfully",
                "shopId", updatedShop.getId(),
                "isActive", updatedShop.isActive()
            )
        );
    }

	@Override
	public ResponseEntity<?> getAllShops() {
	
		List<ShopUser> shops = shopUserRepository.findAll();

		if (shops.isEmpty()) {
			return new ResponseEntity<>("No shops found!", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(shops, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getShopDetailByShopId(Long shopId) {
		ShopUser shopUser = shopUserRepository.findById(shopId).get();
		
		return new ResponseEntity<>(shopUser, HttpStatus.OK);
	}

	@Override
    public List<ShopService> getServicesByShopId(Long shopId) {
        return shopServiceRepository.findByShopUserId(shopId); // Single return statement
    }

	@Override
	public ResponseEntity<?> getAllService() {
		List<ShopService> all = shopServiceRepository.findAll(); 
		return new ResponseEntity<>(all,HttpStatus.OK);
	}

}
