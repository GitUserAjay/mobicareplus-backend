package com.MobiCarePlus.in.MobiCarePlus.request;

import lombok.Data;

@Data
public class ServiceRequest {
    private String name;
    private String category;
    private String description;
    private Double price;
    private Integer discount;
    private Integer estimatedTime;
    private String contact;
    private String location;
    private Boolean available;
    private String imageBase64;
    private String imageName;
    private Long userId; 
}