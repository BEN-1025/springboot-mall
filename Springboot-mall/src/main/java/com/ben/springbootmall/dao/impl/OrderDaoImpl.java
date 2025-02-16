package com.ben.springbootmall.dao.impl;

import com.ben.springbootmall.dao.OrderDao;
import com.ben.springbootmall.model.Order;
import com.ben.springbootmall.model.OrderItem;
import com.ben.springbootmall.rowmapper.OrderItemRowMapper;
import com.ben.springbootmall.rowmapper.OrderRowMapper;
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
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Order getOrderById(Integer orderId) {
        String sql="select order_id,user_id,total_amount,created_date,last_modified_date from `order` where order_id=:orderId";
        Map<String,Object> map=new HashMap<>();
        map.put("orderId",orderId);

        List<Order> orderList=namedParameterJdbcTemplate.query(sql,map, new OrderRowMapper());

        if(orderList.size()>0){
            return orderList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql="select oi.order_item_id,oi.order_id,oi.product_id,oi.quantity,oi.amount,p.product_name,p.image_url from order_item as oi left join product as p on oi.product_id=p.product_id where oi.order_id=:orderId";
        Map<String,Object> map=new HashMap<>();
        map.put("orderId",orderId);
        List<OrderItem> orderItemList=namedParameterJdbcTemplate.query(sql,map, new OrderItemRowMapper());
        return orderItemList;
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql="insert into `order`(user_id,total_amount,created_date,last_modified_date) values (:userId,:totalAmount,:createdDate,:lastModifiedDate)";
        Map<String,Object> map= new HashMap<>();
        map.put("userId",userId);
        map.put("totalAmount",totalAmount);

        Date now= new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder= new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        int orderId= keyHolder.getKey().intValue();
        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {

        for(OrderItem orderitem:orderItemList){
            String sql="insert into order_item(order_id,product_id,quantity,amount)values(:orderId,:productId,:quantity,:amount)";
            Map<String,Object> map= new HashMap<>();
            map.put("orderId",orderId);
            map.put("productId",orderitem.getProductId());
            map.put("quantity",orderitem.getQuantity());
            map.put("amount",orderitem.getAmount());

            namedParameterJdbcTemplate.update(sql,map);
        }
    }
}
