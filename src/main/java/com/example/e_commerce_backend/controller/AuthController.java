package com.example.e_commerce_backend.controller;


import com.example.e_commerce_backend.appconfig.JwtProvider;
import com.example.e_commerce_backend.exception.UserException;
import com.example.e_commerce_backend.model.Cart;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.repo.UserRepo;
import com.example.e_commerce_backend.request.LoginRequest;
import com.example.e_commerce_backend.response.AuthRespo;
import com.example.e_commerce_backend.service.CartService;
import com.example.e_commerce_backend.service.CustmorUserServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private UserRepo userRepo;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private CustmorUserServiceImp userService;
    private CartService cartService;


    @PostMapping("/signup")
    public ResponseEntity<AuthRespo> creteUserHandler(@RequestBody User user)throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstString=user.getFirstName();
        String lastNString=user.getLastName();

        User isEmailExist = userRepo.findUserByEmail(email);

        if (isEmailExist!=null){
            throw new UserException("Email are Already Used With Anther Account!!");
        }
        User createUser = new User();
        createUser.setEmail(email);
        createUser.setPassword(passwordEncoder.encode(password));
        createUser.setFirstName(firstString);
        createUser.setLastName(lastNString);
        createUser.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepo.save(createUser);
        Cart cart = cartService.createCart(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token =  jwtProvider.generateToken(authentication);

        AuthRespo authRespo = new AuthRespo();
        authRespo.setJwt(token);
        authRespo.setMessage("Sign-Up Success");

        return new ResponseEntity<AuthRespo>(authRespo, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public List<User> getusers(){
        return  userRepo.findAll();
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthRespo>loginUserHandler(@RequestBody LoginRequest loginRequest)throws UserException{
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = auth(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token =  jwtProvider.generateToken(authentication);

        AuthRespo authRespo = new AuthRespo();
        authRespo.setJwt(token);
        authRespo.setMessage("Sign-In Success");

        return new ResponseEntity<AuthRespo>(authRespo, HttpStatus.CREATED);
    }

    private Authentication auth(String username,String password)throws UserException{
        UserDetails userDetails = userService.loadUserByUsername(username);
        if (userDetails==null){
            throw new BadCredentialsException("Invalid Username!!");
        }
        if (!(passwordEncoder.matches(password, userDetails.getPassword()))){
            throw new BadCredentialsException("Invalid password!!");
        }
        return new  UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
