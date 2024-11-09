package com.example.e_commerce_backend.exception;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class UserException extends Exception{
    public UserException(String message){
        super(message);
    }
}
