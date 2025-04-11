package com.MobiCarePlus.in.MobiCarePlus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MobiCarePlus.in.MobiCarePlus.entity.Role;
import com.MobiCarePlus.in.MobiCarePlus.entity.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByName(RoleType roleUser);

	

}
