package com.MobiCarePlus.in.MobiCarePlus.request;

import lombok.Data;

@Data
public class UpdateShopProfileRequest {
	 private String shopName;
	    private String ownerName;
	    private String businessType;
	    private String gstNumber;
	    private String panNumber;
	    private String shopLogoUrl;
	    private String websiteUrl;
	    private Long userId;  
}
