package com.ecommerce2.demo.Services;

import com.ecommerce2.demo.DTO.ProductDto;

import java.io.IOException;

public interface IProductService {
    ProductDto getProductById(Long id) throws IOException;
}
