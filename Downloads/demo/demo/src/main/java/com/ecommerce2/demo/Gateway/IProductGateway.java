package com.ecommerce2.demo.Gateway;

import com.ecommerce2.demo.DTO.ProductDto;

import java.io.IOException;

public interface IProductGateway {
    ProductDto getProductById(Long id) throws IOException;
}
