package com.MobiCarePlus.in.MobiCarePlus.request;

import lombok.Data;

@Data
public class PlaceOrderRequest {
    private Long customerId;
    private String productName;
    private int quantity;
    private double price;

    
}
