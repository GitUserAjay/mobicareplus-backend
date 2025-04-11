package com.MobiCarePlus.in.MobiCarePlus.request;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class AddProductOwner {
		private Long userId;
	    private String shopName;
	    private String shopType;
	    private String shopAddress;
	    private String city;
	    private String state;
	    private String pincode;
	    private String contactNumber;
	    private String website;

	    private String mapsLocation;

	    // Owner Details
	    private String ownerName;
	    private String ownerContact;
	    private String ownerEmail;
	    private String ownerAddress;
	    @Lob
	    private String aadhaarPan; 
	    private String gstNumber;
	    @Lob
	    private String shopLicense; //3
	    @Lob
	    private String fssaiLicense; //4
	    @Lob
	    private String panCard;
	    private String bankDetails;
	    private String upiId;//5

}
