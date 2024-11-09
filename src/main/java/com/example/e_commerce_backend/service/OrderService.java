package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.exception.OrderException;
import com.example.e_commerce_backend.model.Address;
import com.example.e_commerce_backend.model.Order;
import com.example.e_commerce_backend.model.User;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address shippingAdress);
    public Order findOrderByld(Long orderld) throws OrderException;
    public List<Order> usersOrderHistory(Long userld);
    public Order placedOrder(Long orderld) throws OrderException;
    public Order confirmedOrder(Long orderld)throws OrderException;
    public Order shippedOrder(Long orderld) throws OrderException;
    public Order deliveredOrder(Long orderld) throws OrderException;
    public Order cancledOrder(Long orderld) throws OrderException;
    public List<Order> getAllOrder();
    public String deleteById(Long orderId);
}
