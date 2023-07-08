package com.eric.springbootmall.dao.impl;

import com.eric.springbootmall.dao.ProductDao;
import com.eric.springbootmall.dto.ProductRequest;
import com.eric.springbootmall.model.Product;
import com.eric.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        StringBuilder strB = new StringBuilder();
        strB.append("select product_id, ");
        strB.append("product_name, ");
        strB.append("category, ");
        strB.append("image_url, ");
        strB.append("price, ");
        strB.append("stock, ");
        strB.append("description, ");
        strB.append("created_date, ");
        strB.append("last_modified_date ");
        strB.append("from product ");
        strB.append("where product_id=:productId ");

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(strB.toString(), map, new ProductRowMapper());

        System.out.println(productList);
        if (!productList.isEmpty()) {
            return productList.get(0);
        }
        return null;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        StringBuilder strB = new StringBuilder();
        strB.append("insert into product(product_name, ");
        strB.append("category, ");
        strB.append("image_url, ");
        strB.append("price, ");
        strB.append("stock, ");
        strB.append("description, ");
        strB.append("created_date, ");
        strB.append("last_modified_date) ");
        strB.append("values (:productName, ");
        strB.append(":category, ");
        strB.append(":imageUrl, ");
        strB.append(":price, ");
        strB.append(":stock, ");
        strB.append(":description, ");
        strB.append(":createdDate, ");
        strB.append(":lastModifiedDate) ");

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();


        namedParameterJdbcTemplate.update(strB.toString(), new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        StringBuilder strB = new StringBuilder();
        strB.append("update product ");
        strB.append("set product_name=:productName, ");
        strB.append("category=:category, ");
        strB.append("image_url=:imageUrl, ");
        strB.append("price=:price, ");
        strB.append("stock=:stock, ");
        strB.append("description=:description, ");
        strB.append("last_modified_date=:lastModifiedDate ");
        strB.append("where product_id=:productId ");

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(strB.toString(),map);

    }

    @Override
    public void deleteProduct(Integer productId) {
        StringBuilder strB = new StringBuilder();
        strB.append("delete from product ");
        strB.append("where product_id=:productId ");
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(strB.toString(),map);

    }
}
