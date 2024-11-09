package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.exception.ProductExepton;
import com.example.e_commerce_backend.model.Product;
import com.example.e_commerce_backend.model.Rating;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.repo.RatingRepo;
import com.example.e_commerce_backend.request.RatingRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RatingServiceImp implements RatingService{

    RatingRepo ratingRepo;
    ProductService productService;

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductExepton {

        Product product =productService.findProductByid(req.getProductId());

        Rating rating = new Rating();
        rating.setUser(user);
        rating.setProduct(product);
        rating.setCreatedAt(LocalDateTime.now());
        rating.setRating(req.getRating());

        return ratingRepo.save(rating);
    }

    @Override
    public List<Rating> getProductRating(Long productId) {
        return ratingRepo.getAllProductsRating(productId);
    }
}
