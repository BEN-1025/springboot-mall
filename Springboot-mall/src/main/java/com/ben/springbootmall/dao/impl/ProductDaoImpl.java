package com.ben.springbootmall.dao.impl;

import com.ben.springbootmall.constant.ProductCategory;
import com.ben.springbootmall.dao.ProductDao;
import com.ben.springbootmall.dto.ProductRequest;
import com.ben.springbootmall.model.Product;
import com.ben.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> getProducts(ProductCategory category,String search) {
        String sql="select product_id ,product_name, category, image_url, price, stock, description, created_date, last_modified_date from product where 1=1";
        Map<String,Object> map=new HashMap<>();
        if(category !=null){
            sql=sql+" and category=:category";
            map.put("category",category.name());
        }
        if (search != null){
            sql=sql+" and product_name like :search";
            map.put("search","%"+search+"%");
        }

        List<Product> productList=namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());
        return productList;
    }

    @Override
    public Product getProductById(Integer productId) {
        String sql="select product_id ,product_name, category, image_url, price, stock, description, created_date, last_modified_date from product where product_id = :productId";
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("productId", productId);

        List<Product> productList=namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

        if(productList.size()>0){
            return productList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql="insert into product(product_name, category, image_url, price, stock, description, created_date, last_modified_date) values(:productName,:category,:imageUrl,:price,:stock,:description,:created_date,:last_modified_date)";
        Map<String,Object> map=new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageURL());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date now=new Date();
        map.put("created_date",now);
        map.put("last_modified_date",now);

        KeyHolder keyHolder=new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        int productId=keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer ProductId, ProductRequest productRequest) {
        String sql="update product set product_name=:productName,category=:category,image_url=:imageUrl,price=:price,stock=:stock,description=:description,last_modified_date=:last_modified_date where product_id=:productId";

        Map<String,Object> map=new HashMap<>();
        map.put("productId", ProductId);
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageURL());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        map.put("last_modified_date",new Date());
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deleteProductById(Integer ProductId) {
        String sql="delete from product where product_id = :productId";
        Map<String,Object> map=new HashMap<>();
        map.put("productId", ProductId);

        namedParameterJdbcTemplate.update(sql,map);
    }
}
