package com.example.e_commerce_backend.repo;

import com.example.e_commerce_backend.exception.ProductExepton;
import com.example.e_commerce_backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review,Long> {

    @Query("SELECT r FROM Review r WHERE r.product.id=:productId")
    public List<Review> getAllProductReview(@Param("productId")Long productId);
}
