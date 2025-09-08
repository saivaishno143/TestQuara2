package com.ecomm2.ecommerceapp.Gateway;

import com.ecomm2.ecommerceapp.Dto.FakeStoreProductResponseDTO;
import com.ecomm2.ecommerceapp.Dto.ProductDto;
import com.ecomm2.ecommerceapp.Gateway.Api.FakeStoreProductApi;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class FakeStoreProductGateWay implements IProductGateway{

    private final FakeStoreProductApi fakeStoreProductApi;

    public FakeStoreProductGateWay(FakeStoreProductApi fakeStoreProductApi) {
        this.fakeStoreProductApi = fakeStoreProductApi;
    }

    @Override
    public ProductDto getProductById(Long id) throws IOException {
        FakeStoreProductResponseDTO response=this.fakeStoreProductApi.getFakeProduct(id).execute().body();
        if(response==null){
            throw new IOException("Product not Found");
        }
        return response.getProduct();
    }
}
