package com.ecommerce2.demo.Controllers;

import com.ecommerce2.demo.DTO.ProductDto;
import com.ecommerce2.demo.Services.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/products")
public class ProductController {

    private final IProductService iProductService;

    public ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) throws IOException {
        ProductDto response=this.iProductService.getProductById(id);

        return ResponseEntity.ok(response);
    }

}
