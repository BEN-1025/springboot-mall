package com.ben.springbootmall.service;

import com.ben.springbootmall.dto.CreateOrderRequest;
import com.ben.springbootmall.dto.OrderQueryParams;
import com.ben.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
