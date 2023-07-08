package com.eric.springbootmall.service.impl;

import com.eric.springbootmall.dao.ProductDao;
import com.eric.springbootmall.dto.ProductQueryParam;
import com.eric.springbootmall.dto.ProductRequest;
import com.eric.springbootmall.model.Product;
import com.eric.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }

    @Override
    public List<Product> getProducts(ProductQueryParam productQueryParam) {
        return  productDao.getProducts(productQueryParam);
    }

    @Override
    public Integer countProduct(ProductQueryParam productQueryParam) {
        return  productDao.countProduct(productQueryParam);
    }
}
