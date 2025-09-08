package com.ecomm2.ecommerceapp.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FakeStoreProductResponseDTO{
	private ProductDto product;
	private String message;
	private String status;
}