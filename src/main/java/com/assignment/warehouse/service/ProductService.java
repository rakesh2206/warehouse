package com.assignment.warehouse.service;

import com.assignment.warehouse.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Iterable<Product> getProducts();

    Product save(Product pProduct);

    Iterable<Product> save(List<Product> pProducts);

    Product findById(Integer prod_id);

    void deleteById(Integer prod_id);

}
