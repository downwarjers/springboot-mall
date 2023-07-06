package com.eric.springbootmall.service;

import com.eric.springbootmall.dto.ProductRequest;
import com.eric.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
