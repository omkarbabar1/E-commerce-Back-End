package com.example.e_commerce_backend.controller;

import com.example.e_commerce_backend.model.Product;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.request.CreateProductReq;
import com.example.e_commerce_backend.service.ProductService;
import com.example.e_commerce_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class AdminProductController {

    ProductService productService;
    UserService userService;

    @PostMapping("/admin/create")
    public ResponseEntity<Product> createProduct(@RequestBody()CreateProductReq req, @RequestHeader("Authorization")String jwt)throws Exception  {

        User user=userService.findUserProfileByJwt(jwt);

        Product product = productService.createProduct(req);

        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }
}
