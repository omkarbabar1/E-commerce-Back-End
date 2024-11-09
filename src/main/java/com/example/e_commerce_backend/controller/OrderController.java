package com.example.e_commerce_backend.controller;


import com.example.e_commerce_backend.exception.OrderException;
import com.example.e_commerce_backend.exception.UserException;
import com.example.e_commerce_backend.model.Address;
import com.example.e_commerce_backend.model.Order;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.service.OrderService;
import com.example.e_commerce_backend.service.OrderServiceImpl;
import com.example.e_commerce_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private OrderServiceImpl orderService;
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress, @RequestHeader("Authorization") String jwt)throws UserException {
        User user = userService.findUserProfileByJwt(jwt);

        Order orders = orderService.createOrder(user,shippingAddress);

        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistory(@RequestHeader("Authorization")String jwt)throws UserException{
        User user = userService.findUserProfileByJwt(jwt);

        List<Order> order = orderService.usersOrderHistory(user.getId());

        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderId,@RequestHeader("Authorization")String jwt)throws UserException , OrderException {
        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.findOrderByld(orderId);

        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderld}/ship")
    public ResponseEntity<Order> ShippedOrderHandler(@PathVariable Long orderld,
                                                     @RequestHeader ("Authorization") String jwt) throws OrderException {
        Order order = orderService.shippedOrder(orderld);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
