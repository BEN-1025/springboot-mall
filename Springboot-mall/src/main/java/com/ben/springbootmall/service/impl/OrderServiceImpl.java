package com.ben.springbootmall.service.impl;

import com.ben.springbootmall.dao.OrderDao;
import com.ben.springbootmall.dao.ProductDao;
import com.ben.springbootmall.dto.BuyItem;
import com.ben.springbootmall.dto.CreateOrderRequest;
import com.ben.springbootmall.model.OrderItem;
import com.ben.springbootmall.model.Product;
import com.ben.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        int totalAmount=0;
        List<OrderItem> orderItemList=new ArrayList<>();
        for (BuyItem buyItem:createOrderRequest.getBuyItemList()){
            Product product =productDao.getProductById(buyItem.getProductId());
            //計算總金額
            int amount= buyItem.getQuantity()*product.getPrice();
            totalAmount=totalAmount+amount;

            //轉換BuyItem to OrderItem
            OrderItem orderItem=new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);

        }
        //創建訂單
        Integer orderId=orderDao.createOrder(userId,totalAmount);

        orderDao.createOrderItems(orderId,orderItemList);

        return orderId;

    }
}
