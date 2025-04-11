package com.MobiCarePlus.in.MobiCarePlus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MobiCarePlus.in.MobiCarePlus.entity.Role;
import com.MobiCarePlus.in.MobiCarePlus.entity.ShopUser;
import com.MobiCarePlus.in.MobiCarePlus.entity.UserInfo;

public interface ShopUserRepository extends JpaRepository<ShopUser, Long> {

	ShopUser findByUserInfo(UserInfo userInfo);

	Optional<Role> findByShopName(String string);

}
