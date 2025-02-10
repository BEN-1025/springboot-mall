package com.ben.springbootmall.dao;

import com.ben.springbootmall.dto.ProductRequest;
import com.ben.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer ProductId);

    Integer createProduct(ProductRequest productRequest);
}
