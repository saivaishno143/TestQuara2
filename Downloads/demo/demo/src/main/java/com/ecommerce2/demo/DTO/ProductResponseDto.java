package com.ecommerce2.demo.DTO;

import lombok.Data;

@Data
public class ProductResponseDto{
	private ProductDto product;
	private String message;
	private String status;
}