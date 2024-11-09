package com.example.e_commerce_backend.controller;


import com.example.e_commerce_backend.exception.OrderException;
import com.example.e_commerce_backend.model.Order;
import com.example.e_commerce_backend.service.OrderService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@AllArgsConstructor
public class AdminOrderController {

    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrderHandler(@RequestHeader("Authorization") String jwt){

        List<Order> orders=orderService.getAllOrder();

        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}/confirmed")
    public ResponseEntity<Order> confirmedOrderHandler(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt)throws OrderException {

        Order order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }
}
