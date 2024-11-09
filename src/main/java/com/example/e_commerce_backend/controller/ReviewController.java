package com.example.e_commerce_backend.controller;


import com.example.e_commerce_backend.exception.ProductExepton;
import com.example.e_commerce_backend.exception.UserException;
import com.example.e_commerce_backend.model.Review;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.request.ReviewReq;
import com.example.e_commerce_backend.service.ProductReview;
import com.example.e_commerce_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@AllArgsConstructor
public class ReviewController {

    private ProductReview productReview;
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Review> createReview(@RequestBody ReviewReq req,@RequestHeader("Authorization")String jwt)throws UserException, ProductExepton{

        User user=userService.findUserProfileByJwt(jwt);

        Review review=productReview.ceateReview(req,user);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductReview(@PathVariable Long productId,@RequestHeader("Authorization")String jwt)throws UserException{

        User user = userService.findUserProfileByJwt(jwt);
        List<Review> reviews = productReview.getAllProductReview(productId);

        return new ResponseEntity<>(reviews,HttpStatus.ACCEPTED);
    }
}
