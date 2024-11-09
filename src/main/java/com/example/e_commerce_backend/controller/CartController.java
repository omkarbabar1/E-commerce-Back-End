package com.example.e_commerce_backend.controller;

import com.example.e_commerce_backend.exception.UserException;
import com.example.e_commerce_backend.model.Cart;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.service.CartService;
import com.example.e_commerce_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {

    //16:46 time

    private CartService cartService;
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt)throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.ACCEPTED);
    }
}
