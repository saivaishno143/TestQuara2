package com.ecommerce2.demo.Gateway.Api;

import com.ecommerce2.demo.DTO.ProductDto;
import com.ecommerce2.demo.DTO.ProductResponseDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.IOException;

public interface FakeStoreProductApi {
    @GET("products/{id}")
    Call<ProductResponseDto> getFakeProduct(@Path("id") Long id) throws IOException;

}
