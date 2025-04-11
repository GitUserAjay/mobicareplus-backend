package com.MobiCarePlus.in.MobiCarePlus.request;

import lombok.Data;

@Data
public class RegisterRequest {
	private String email;

    private String name;
    private String phone;
    private String password;
    private String address;
    
}
