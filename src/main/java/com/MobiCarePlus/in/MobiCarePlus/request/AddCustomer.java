package com.MobiCarePlus.in.MobiCarePlus.request;

import lombok.Data;

@Data
public class AddCustomer {
	private String email;
	private String password;
	private String mobileNumber;
	private String fullName;
	private String contactNumber;
	private String address;
	private String city;
	private String state;
	private String pinCode;
}
