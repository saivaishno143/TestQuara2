package com.ecommerce2.demo.Gateway;

import com.ecommerce2.demo.DTO.ProductDto;
import com.ecommerce2.demo.DTO.ProductResponseDto;
import com.ecommerce2.demo.Gateway.Api.FakeStoreProductApi;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProductGateway implements IProductGateway {

    private final FakeStoreProductApi fakeStoreProductApi;

    public ProductGateway(FakeStoreProductApi fakeStoreProductApi) {
        this.fakeStoreProductApi = fakeStoreProductApi;
    }

    @Override
    public ProductDto getProductById(Long id) throws IOException {
        ProductResponseDto response = this.fakeStoreProductApi.getFakeProduct(id).execute().body();

        if(response==null){
            throw new IOException("Data not Fetched");
        }

        return response.getProduct();

    }
}
