package com.MobiCarePlus.in.MobiCarePlus.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopRegisterRequest {
	
	    private String shopName;
	    private String ownerName;
	    private String phone;
	    private String email;
	    private String password;
	    private String businessType;
	    private String address;
	    private String image; 
    
}
