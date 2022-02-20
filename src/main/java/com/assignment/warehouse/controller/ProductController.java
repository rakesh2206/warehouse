package com.assignment.warehouse.controller;

import com.assignment.warehouse.domain.Product;
import com.assignment.warehouse.service.ProductServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    private final ProductServiceImpl vProductService;

    public ProductController(ProductServiceImpl pProductService) {
        this.vProductService = pProductService;
    }

    @GetMapping("/Products")
    public Iterable<Product> getProducts() {
        return vProductService.getProducts();
    }

    @PostMapping("/addProducts")
    public HttpStatus addProduct(@RequestBody String pJsonPath) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Product>> typeReference = new TypeReference<List<Product>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/products.json");
        try {
            List<Product> vProducts = mapper.readValue(inputStream, typeReference);
            vProductService.save(vProducts);
            System.out.println("Products Updated");
            return HttpStatus.OK;

        } catch (IOException e) {
            System.out.println("Unable to Save Products " + e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping("/Product/{prod_id}")
    public HttpStatus findByProductId(@PathVariable Integer prod_id) {
        try {
            Product vProduct = vProductService.findById(prod_id);
            System.out.println("Products found" + vProduct);
            return HttpStatus.OK;

        } catch (Exception e) {
            System.out.println("Unable to Delete Products " + e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping("/deleteProduct/{prod_id}")
    public HttpStatus deleteByProductId(@PathVariable Integer prod_id) {
        try {
            vProductService.deleteById(prod_id);
            System.out.println("Products deleted" );
            return HttpStatus.OK;

        } catch (Exception e) {
            System.out.println("Unable to Delete Products " + e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
