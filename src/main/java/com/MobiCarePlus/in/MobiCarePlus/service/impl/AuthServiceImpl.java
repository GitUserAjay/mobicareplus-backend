package com.MobiCarePlus.in.MobiCarePlus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.MobiCarePlus.in.MobiCarePlus.entity.Role;
import com.MobiCarePlus.in.MobiCarePlus.entity.RoleType;
import com.MobiCarePlus.in.MobiCarePlus.entity.ShopUser;
import com.MobiCarePlus.in.MobiCarePlus.entity.UserInfo;
import com.MobiCarePlus.in.MobiCarePlus.repository.RoleRepository;
import com.MobiCarePlus.in.MobiCarePlus.repository.ShopUserRepository;
import com.MobiCarePlus.in.MobiCarePlus.repository.UserInfoRepository;
import com.MobiCarePlus.in.MobiCarePlus.request.LoginRequest;
import com.MobiCarePlus.in.MobiCarePlus.request.RegisterRequest;
import com.MobiCarePlus.in.MobiCarePlus.request.ShopRegisterRequest;
import com.MobiCarePlus.in.MobiCarePlus.response.BaseResponse;
import com.MobiCarePlus.in.MobiCarePlus.response.LoginResponse;
import com.MobiCarePlus.in.MobiCarePlus.service.AuthService;
import com.MobiCarePlus.in.MobiCarePlus.util.JwtUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    private UserInfoRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ShopUserRepository shopUserRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        System.out.println("Login request received: " + request);
        
        try {
            // Authenticate user
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

            // Extract roles
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            // Generate token with roles
            String token = jwtUtil.generateToken(userDetails);

            // Fetch userId from UserInfo entity
            Optional<UserInfo> byEmail = userRepository.findByEmail(userDetails.getUsername());
            if (byEmail.isEmpty()) {
                return ResponseEntity.badRequest().body(new LoginResponse("User not found", null, null, null));
            }

            // Prepare response
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setUsername(userDetails.getUsername());
            response.setRoles(roles);
            response.setUserId(byEmail.get().getId());

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(new LoginResponse("Invalid email or password", null, null, null));
        }
    }

    @Override
    public ResponseEntity<BaseResponse> registerUser(RegisterRequest request) {
        BaseResponse response = new BaseResponse();

        // Check if email already exists
        Optional<UserInfo> byEmail = userRepository.findByEmail(request.getEmail());
        if (byEmail.isPresent()) {
            response.setMessage("User already exists with this email.");
            response.setStatusCode("0");
            return ResponseEntity.badRequest().body(response);
        }

        // Create new UserInfo instance
        UserInfo user = new UserInfo();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Fetch ROLE_USER from the database
        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found: ROLE_USER"));

        // ✅ Assign the fetched role to user
        user.setRoles(Collections.singleton(userRole));

        // ✅ Save user
        userRepository.save(user);
        response.setMessage("User registered successfully!");
        response.setStatusCode("1");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse> registerShop(ShopRegisterRequest request) {
        BaseResponse response = new BaseResponse();

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            response.setMessage("Shop already registered with this email.");
            response.setStatusCode("0");
            return ResponseEntity.badRequest().body(response);
        }

        // Save UserInfo
        UserInfo user = new UserInfo();
        user.setEmail(request.getEmail());
        user.setName(request.getOwnerName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        Role userRole = roleRepository.findByName(RoleType.ROLE_SHOP)
                .orElseThrow(() -> new RuntimeException("Role not found: ROLE_USER"));
        user.setRoles(Collections.singleton(userRole));
        
        userRepository.save(user);

        // Save ShopUser with reference to UserInfo
        ShopUser shopUser = new ShopUser();
        shopUser.setShopName(request.getShopName());
        shopUser.setBusinessType(request.getBusinessType());
        shopUser.setCreatedAt(LocalDateTime.now());
        shopUser.setImage(request.getImage());
        shopUser.setActive(Boolean.FALSE);
        shopUser.setUserInfo(user); // Link UserInfo

        shopUserRepository.save(shopUser);
        response.setMessage("Shop registered successfully!");
        response.setStatusCode("1");
        return ResponseEntity.ok(response);
    }

}
