package com.eric.springbootmall.dao.impl;

import com.eric.springbootmall.dao.ProductDao;
import com.eric.springbootmall.model.Product;
import com.eric.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        map.put("productId",productId);

        List<Product> productList = namedParameterJdbcTemplate.query(strB.toString(), map, new ProductRowMapper());

        System.out.println(productList);
        if (!productList.isEmpty()) {
            return productList.get(0);
        }
        return null;
    }
}
