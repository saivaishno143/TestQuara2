package com.ecomm2.ecommerceapp.Gateway;

import com.ecomm2.ecommerceapp.Dto.FakeStoreProductResponseDTO;
import com.ecomm2.ecommerceapp.Dto.ProductDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
@Primary
public class FakeStoreProductGateway_RestTemplate implements IProductGateway{
    private final RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductGateway_RestTemplate(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public ProductDto getProductById(Long id) throws IOException {
        RestTemplate restTemplate=restTemplateBuilder.build();

        String url="https://fakestoreapi.in/api/products/2";

        ResponseEntity<FakeStoreProductResponseDTO> response= restTemplate.getForEntity(url, FakeStoreProductResponseDTO.class);

        if(response==null){
            throw new IOException("not Fetched dats");
        }
        return response.getBody().getProduct();

    }
}
