package com.MobiCarePlus.in.MobiCarePlus.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MobiCarePlus.in.MobiCarePlus.entity.Product;
import com.MobiCarePlus.in.MobiCarePlus.entity.Role;
import com.MobiCarePlus.in.MobiCarePlus.entity.ShopUser;

public interface ProductReposistory extends JpaRepository<Product, Long>{

	List<Product> findAllByShopUser(ShopUser shopUser);

	Optional<Role> findByName(String string);


}
