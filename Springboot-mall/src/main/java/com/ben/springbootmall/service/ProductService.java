package com.ben.springbootmall.service;

import com.ben.springbootmall.dto.ProductRequest;
import com.ben.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
