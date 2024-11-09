package com.example.e_commerce_backend.repo;

import com.example.e_commerce_backend.model.OrderIteam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderIteam,Long> {
}
