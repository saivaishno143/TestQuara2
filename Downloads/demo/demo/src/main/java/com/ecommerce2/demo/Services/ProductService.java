package com.ecommerce2.demo.Services;

import com.ecommerce2.demo.DTO.ProductDto;
import com.ecommerce2.demo.Gateway.IProductGateway;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProductService implements IProductService {
    private final IProductGateway iProductGateway;

    public ProductService(IProductGateway iProductGateway) {
        this.iProductGateway = iProductGateway;
    }

    @Override
    public ProductDto getProductById(Long id) throws IOException {
        return this.iProductGateway.getProductById(id);
    }
}
