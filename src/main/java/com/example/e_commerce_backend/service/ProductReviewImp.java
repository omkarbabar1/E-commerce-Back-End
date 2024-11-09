package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.exception.ProductExepton;
import com.example.e_commerce_backend.exception.UserException;
import com.example.e_commerce_backend.model.Product;
import com.example.e_commerce_backend.model.Review;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.repo.ReviewRepo;
import com.example.e_commerce_backend.request.ReviewReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductReviewImp implements ProductReview{

    ReviewRepo reviewRepo;
    ProductService productService;

    @Override
    public Review ceateReview(ReviewReq req, User user)throws ProductExepton {
        Product product = productService.findProductByid(req.getProductId());
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());


        return reviewRepo.save(review);
    }

    @Override
    public List<Review> getAllProductReview(Long productId) {
        return reviewRepo.getAllProductReview(productId);
    }
}
