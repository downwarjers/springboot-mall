package com.eric.springbootmall.controller;

import com.eric.springbootmall.constant.ProductCategory;
import com.eric.springbootmall.dto.ProductQueryParam;
import com.eric.springbootmall.dto.ProductRequest;
import com.eric.springbootmall.model.Product;
import com.eric.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            //查詢條件
            @RequestParam(required = false) ProductCategory category,//種類
            @RequestParam(required = false) String search,//搜尋
            //排序
            @RequestParam(defaultValue = "created_date") String orderBy,//欄位
            @RequestParam(defaultValue = "desc") String sort,//排法
            //分頁
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,//資料筆數
            @RequestParam(defaultValue = "0") @Min(0) Integer offset//略過筆數

    ) {
        ProductQueryParam productQueryParam = new ProductQueryParam();
        productQueryParam.setCategory(category);
        productQueryParam.setSearch(search);
        productQueryParam.setOrderBy(orderBy);
        productQueryParam.setSort(sort);
        productQueryParam.setLimit(limit);
        productQueryParam.setOffset(offset);
        List<Product> list = productService.getProducts(productQueryParam);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.ok().body(product);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, @RequestBody @Valid ProductRequest productRequest) {
        if (productService.getProductById(productId) != null) {
            productService.updateProduct(productId, productRequest);
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok().body(product);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
