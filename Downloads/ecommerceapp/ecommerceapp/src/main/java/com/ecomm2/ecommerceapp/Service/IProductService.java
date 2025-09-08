package com.ecomm2.ecommerceapp.Service;

import com.ecomm2.ecommerceapp.Dto.ProductDto;

import java.io.IOException;

public interface IProductService {
    ProductDto getProductByID(Long id) throws IOException;
}
