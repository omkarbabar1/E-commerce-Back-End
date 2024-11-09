package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.exception.CartltemExeption;
import com.example.e_commerce_backend.exception.UserException;
import com.example.e_commerce_backend.model.Cart;
import com.example.e_commerce_backend.model.CartItem;
import com.example.e_commerce_backend.model.Product;

public interface CartltemService {
    public CartItem createCartlteam (CartItem cartItem);
    public CartItem updateCartIteam(Long userId, Long id, CartItem cartItem)throws CartltemExeption, UserException;
    public CartItem isCartItteamExist(Cart cart, Product product, String size , Long userId);
    public void removeCartIteam(Long id,Long CartIteamId)throws CartltemExeption ,UserException;
    public CartItem findCartIteamById(Long cartIteamId)throws CartltemExeption;
}
