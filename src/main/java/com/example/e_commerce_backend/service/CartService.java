package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.exception.ProductExepton;
import com.example.e_commerce_backend.model.Cart;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.request.AddIteamReq;

public interface CartService {

    public Cart createCart(User user);
    public String  addCartIteam(Long userId, AddIteamReq req)throws ProductExepton;
    public Cart findUserCart(Long userId);
}
