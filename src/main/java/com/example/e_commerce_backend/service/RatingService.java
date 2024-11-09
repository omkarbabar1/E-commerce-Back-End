package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.exception.ProductExepton;
import com.example.e_commerce_backend.model.Rating;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.request.RatingRequest;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest req, User user)throws ProductExepton;
    public List<Rating> getProductRating(Long productId);
}
