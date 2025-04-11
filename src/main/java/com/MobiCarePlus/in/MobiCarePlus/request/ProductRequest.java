package com.MobiCarePlus.in.MobiCarePlus.request;

import java.math.BigDecimal;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class ProductRequest {
	private String name;
	private String category; // "Mobile" or "Accessories"
	private BigDecimal price; // Using BigDecimal for price
	@Lob
	private String image;
	private Long userId;
	private String status; // Available, Out of Stock, Discontinued

}
