package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.exception.UserException;
import com.example.e_commerce_backend.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User findUserById (Long  userId) throws UserException;
    public User findUserProfileByJwt(String jwt) throws UserException;
}
