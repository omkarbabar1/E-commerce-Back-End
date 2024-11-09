package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.exception.OrderException;
import com.example.e_commerce_backend.model.Address;
import com.example.e_commerce_backend.model.Order;
import com.example.e_commerce_backend.model.OrderIteam;
import com.example.e_commerce_backend.model.User;

public interface OrderItemService {

    public Order createOrderItem(User user, Address shipaddress)throws OrderException;
}
