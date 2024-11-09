package com.example.e_commerce_backend.repo;

import com.example.e_commerce_backend.model.Cart;
import com.example.e_commerce_backend.model.CartItem;
import com.example.e_commerce_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartIteamRepo extends JpaRepository<CartItem,Long> {

    @Query("SELECT ci From CartItem ci " +
            "Where ci.cart = :cart " +
            "And ci.product=:product " +
            "And ci.size= :size " +
            "And ci.userId=:userId")
    public CartItem isCartltemExist(@Param("cart") Cart cart,
                                    @Param("product") Product product,
                                    @Param ("size")String size,
                                    @Param("userId")Long userId);
}
