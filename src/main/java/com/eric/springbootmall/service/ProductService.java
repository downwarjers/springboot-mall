package com.eric.springbootmall.service;

import com.eric.springbootmall.dto.ProductRequest;
import com.eric.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProduct(Integer productId);

    List<Product> getProducts();
}
