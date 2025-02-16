package com.ben.springbootmall.service;

import com.ben.springbootmall.dto.CreateOrderRequest;
import com.ben.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
