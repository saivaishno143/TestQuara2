package com.ecomm2.ecommerceapp.Service;

import com.ecomm2.ecommerceapp.Dto.ProductDto;
import com.ecomm2.ecommerceapp.Gateway.IProductGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class FakeStroreProductService  implements IProductService{

    private final IProductGateway ProductGateway;

    public FakeStroreProductService(
             IProductGateway productGateway) {
        ProductGateway = productGateway;
    }

    @Override
    public ProductDto getProductByID(Long id) throws IOException {
        return this.ProductGateway.getProductById(id);
    }
}
