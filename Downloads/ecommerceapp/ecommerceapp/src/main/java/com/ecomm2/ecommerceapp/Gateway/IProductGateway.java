package com.ecomm2.ecommerceapp.Gateway;

import com.ecomm2.ecommerceapp.Dto.ProductDto;

import java.io.IOException;

public interface IProductGateway {
    ProductDto getProductById(Long id) throws IOException;
}
