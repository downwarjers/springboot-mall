package com.eric.springbootmall.dao;

import com.eric.springbootmall.dto.ProductQueryParam;
import com.eric.springbootmall.dto.ProductRequest;
import com.eric.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProduct(Integer productId);

    List<Product> getProducts(ProductQueryParam productQueryParam);

    Integer countProduct(ProductQueryParam productQueryParam);
}
