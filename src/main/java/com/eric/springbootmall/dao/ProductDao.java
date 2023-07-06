package com.eric.springbootmall.dao;

import com.eric.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
