package com.eric.springbootmall.dao;

import com.eric.springbootmall.dto.ProductRequest;
import com.eric.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
}
