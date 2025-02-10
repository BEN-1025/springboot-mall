package com.ben.springbootmall.dao;

import com.ben.springbootmall.constant.ProductCategory;
import com.ben.springbootmall.dto.ProductRequest;
import com.ben.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductCategory category);

    Product getProductById(Integer ProductId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer ProductId,ProductRequest productRequest);

    void deleteProductById(Integer ProductId);
}
