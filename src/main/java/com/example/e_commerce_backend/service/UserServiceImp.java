package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.appconfig.JwtProvider;
import com.example.e_commerce_backend.exception.UserException;
import com.example.e_commerce_backend.model.Product;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService{

    private UserRepo userRepo;
    private JwtProvider jwtProvider;

    UserServiceImp(UserRepo userRepo,JwtProvider jwtProvider){
        this.userRepo=userRepo;
        this.jwtProvider=jwtProvider;
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()){
            return user.get();
        }
        throw new  UserException("User Not Fount with id :"+userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {

        String email = jwtProvider.getEmailFromToken(jwt);

        User user = userRepo.findUserByEmail(email);

        if (user==null){
            throw new UserException("User Not Found With Email: "+email);
        }

        return user;
    }
}
