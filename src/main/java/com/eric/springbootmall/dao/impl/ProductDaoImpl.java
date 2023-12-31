package com.eric.springbootmall.dao.impl;

import com.eric.springbootmall.constant.ProductCategory;
import com.eric.springbootmall.dao.ProductDao;
import com.eric.springbootmall.dto.ProductQueryParam;
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
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "select product_id, " + "product_name, " + "category, " + "image_url, " + "price, " + "stock, " + "description, " + "created_date, " + "last_modified_date " + "from product " + "where product_id=:productId ";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        System.out.println(productList);
        if (!productList.isEmpty()) {
            return productList.get(0);
        }
        return null;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "insert into product(product_name, " + "category, " + "image_url, " + "price, " + "stock, " + "description, " + "created_date, " + "last_modified_date) " + "values (:productName, " + ":category, " + ":imageUrl, " + ":price, " + ":stock, " + ":description, " + ":createdDate, " + ":lastModifiedDate) ";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        if (productRequest.getCategory() != null) {
            map.put("category", productRequest.getCategory().name());
        }
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();


        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "update product " + "set product_name=:productName, " + "category=:category, " + "image_url=:imageUrl, " + "price=:price, " + "stock=:stock, " + "description=:description, " + "last_modified_date=:lastModifiedDate " + "where product_id=:productId ";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("productName", productRequest.getProductName());
        if (productRequest.getCategory() != null) {
            map.put("category", productRequest.getCategory().name());
        }
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "delete from product " + "where product_id=:productId ";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public List<Product> getProducts(ProductQueryParam productQueryParam) {
        String sql = "select product_id, " + "product_name, " + "category, " + "image_url, " + "price, " + "stock, " + "description, " + "created_date, " + "last_modified_date " + "from product " + "where 1=1 ";

        Map<String, Object> map = new HashMap<>();
        //查詢
        sql += addFilteringSql(productQueryParam, map);
        //排序
        sql += " order by " + productQueryParam.getOrderBy() + " " + productQueryParam.getSort();

        //分頁
        sql += " limit :limit offset :offset ";
        map.put("limit", productQueryParam.getLimit());
        map.put("offset", productQueryParam.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList;
    }

    @Override
    public Integer countProduct(ProductQueryParam productQueryParam) {
        String sql = "select count(*) " + "from product " + "where 1=1 ";

        Map<String, Object> map = new HashMap<>();
        //查詢
        sql += addFilteringSql(productQueryParam, map);

        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    private String addFilteringSql(ProductQueryParam productQueryParam, Map<String, Object> map) {
        String sql = "";
        if (productQueryParam.getCategory() != null) {
            sql += "and category=:category ";
            map.put("category", productQueryParam.getCategory().name());
        }
        if (productQueryParam.getSearch() != null) {
            sql += "and product_name like :search ";
            map.put("search", "%" + productQueryParam.getSearch() + "%");
        }
        return sql;
    }

}
