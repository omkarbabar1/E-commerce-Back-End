package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.exception.ProductExepton;
import com.example.e_commerce_backend.model.Review;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.request.ReviewReq;

import java.util.List;

public interface ProductReview {

    public Review ceateReview(ReviewReq req , User user)throws ProductExepton;
    public List<Review> getAllProductReview(Long productId);
}
