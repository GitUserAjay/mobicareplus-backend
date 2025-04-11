package com.MobiCarePlus.in.MobiCarePlus.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MobiCarePlus.in.MobiCarePlus.entity.Role;
import com.MobiCarePlus.in.MobiCarePlus.entity.ShopService;
import com.MobiCarePlus.in.MobiCarePlus.entity.ShopUser;

public interface ShopServiceRepository extends JpaRepository<ShopService, Long>{

	List<ShopService> findAllByShopUser(ShopUser shopUser);

	Optional<Role> findByName(String string);

	List<ShopService> findByShopUserId(Long shopId);

}
