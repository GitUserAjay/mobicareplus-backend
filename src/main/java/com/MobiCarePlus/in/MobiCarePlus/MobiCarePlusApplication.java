package com.MobiCarePlus.in.MobiCarePlus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.MobiCarePlus.in.MobiCarePlus.entity.Role;
import com.MobiCarePlus.in.MobiCarePlus.entity.RoleType;
import com.MobiCarePlus.in.MobiCarePlus.entity.UserInfo;
import com.MobiCarePlus.in.MobiCarePlus.repository.RoleRepository;
import com.MobiCarePlus.in.MobiCarePlus.repository.UserInfoRepository;

@SpringBootApplication
public class MobiCarePlusApplication implements ApplicationRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserInfoRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder; // Ensure you have a PasswordEncoder Bean

	public static void main(String[] args) {
		SpringApplication.run(MobiCarePlusApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// Ensure roles exist
		Arrays.stream(RoleType.values()).forEach(roleType -> {
			roleRepository.findByName(roleType).orElseGet(() -> {
				Role role = new Role();
				role.setName(roleType);
				return roleRepository.save(role);
			});
		});
		System.out.println("✅ Roles loaded successfully!");

		// Create a default admin user if not exists
		if (userRepository.findByEmail("admin@mobicareplus.com").isEmpty()) {
			UserInfo admin = new UserInfo();
			admin.setEmail("admin@mobicareplus.com");
			admin.setName("Admin");
			admin.setPhone("9999999999");
			admin.setPassword(passwordEncoder.encode("admin123")); // Encrypt password
			admin.setAddress("Admin Address");
			admin.setCreatedAt(LocalDateTime.now());
			admin.setUpdatedAt(LocalDateTime.now());

			Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Admin role not found!"));
			admin.setRoles(new HashSet<>(Arrays.asList(adminRole)));

			userRepository.save(admin);
			System.out.println("✅ Default admin user created!");
		} else {
			System.out.println("✅ Admin user already exists.");
		}
	}
}
