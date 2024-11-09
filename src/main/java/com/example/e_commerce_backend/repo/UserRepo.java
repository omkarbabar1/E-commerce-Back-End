package com.example.e_commerce_backend.repo;

import com.example.e_commerce_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {

     User findUserByEmail(String email);


}
