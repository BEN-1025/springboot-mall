package com.ben.springbootmall.dao;

import com.ben.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
