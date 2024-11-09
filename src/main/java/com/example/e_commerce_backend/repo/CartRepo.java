package com.example.e_commerce_backend.repo;

import com.example.e_commerce_backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface CartRepo extends JpaRepository<Cart,Long> {

    @Query("SELECT c FROM Cart c WHERE c.user.id =:userId")
    public Cart findUserById(@Param("userId")Long userId);
}
