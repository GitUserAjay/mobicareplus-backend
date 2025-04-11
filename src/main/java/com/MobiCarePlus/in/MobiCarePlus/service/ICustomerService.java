package com.MobiCarePlus.in.MobiCarePlus.service;

import org.springframework.http.ResponseEntity;

import com.MobiCarePlus.in.MobiCarePlus.request.AddCustomer;
import com.MobiCarePlus.in.MobiCarePlus.response.BaseResponse;

public interface ICustomerService {

	ResponseEntity<BaseResponse> addCustomer(AddCustomer request);

}
