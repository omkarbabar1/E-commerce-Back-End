package com.example.e_commerce_backend.request;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
public class LoginRequest {
    private String email;
    private String password;

}
