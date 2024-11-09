package com.example.e_commerce_backend.controller;


import com.example.e_commerce_backend.exception.ProductExepton;
import com.example.e_commerce_backend.exception.UserException;
import com.example.e_commerce_backend.model.Rating;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.request.RatingRequest;
import com.example.e_commerce_backend.service.RatingService;
import com.example.e_commerce_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ratings")
public class RatingController {

    private UserService userService;
    private RatingService ratingService;


    @PostMapping("/")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req, @RequestHeader("Authorization")String jwt)throws UserException, ProductExepton {


        User usr = userService.findUserProfileByJwt(jwt);

        Rating rating = ratingService.createRating( req,usr);

        return new ResponseEntity<>(rating,HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId, @RequestHeader("Authorization")String jwt)throws UserException,ProductExepton{

        User user = userService.findUserProfileByJwt(jwt);

        List<Rating> ratings =ratingService.getProductRating(productId);

        return new ResponseEntity<>(ratings,HttpStatus.ACCEPTED);
    }
}
