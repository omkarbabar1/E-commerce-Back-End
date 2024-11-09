package com.example.e_commerce_backend.controller;


import com.example.e_commerce_backend.exception.UserException;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> userProfileHandler(@RequestHeader("Authorization")String jwt)throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
