package com.example.e_commerce_backend.controller;


import com.example.e_commerce_backend.exception.ProductExepton;
import com.example.e_commerce_backend.exception.UserException;
import com.example.e_commerce_backend.model.Product;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.service.ProductService;
import com.example.e_commerce_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserProductController {

    private ProductService productService;
    private UserService userService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category, @RequestParam List<String> color , @RequestParam List<String>size,
                                                                      @RequestParam Integer minPrice, @RequestParam Integer maxPrice, @RequestParam Integer minDiscount,
                                                                      @RequestParam String sort, @RequestParam String stock, @RequestParam Integer pageNumber,
                                                                      @RequestParam Integer pageSize){

        Page<Product> res  = productService.getAllProduct(category,color,size
                ,minPrice,maxPrice,minDiscount,sort,stock,pageNumber,pageSize);

        
        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId)throws ProductExepton {

        Product product = productService.findProductByid(productId);

        return new ResponseEntity<>(product,HttpStatus.ACCEPTED);
    }

    @GetMapping("/user/profile")
    public ResponseEntity<User> findUserByJwt(@RequestHeader("Authorization")String jwt)throws UserException {

        System.out.println(jwt);
        User user=userService.findUserProfileByJwt(jwt);
        System.out.println(user);

        return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
    }
}
