package com.example.e_commerce_backend.controller;


import com.example.e_commerce_backend.exception.CartltemExeption;
import com.example.e_commerce_backend.exception.ProductExepton;
import com.example.e_commerce_backend.exception.UserException;
import com.example.e_commerce_backend.model.CartItem;
import com.example.e_commerce_backend.model.Product;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.request.AddIteamReq;
import com.example.e_commerce_backend.service.CartService;
import com.example.e_commerce_backend.service.CartltemService;
import com.example.e_commerce_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartIteamController {
    private CartService cartService;
    private CartltemService cartltemService;
    private UserService userService;


    @PutMapping("/update/{cartIteamId}")
    public ResponseEntity<CartItem> updateProduct(@RequestHeader("Authorization")String jwt,
                                                 @RequestBody CartItem cartItem,
                                                 @PathVariable Long cartIteamId)throws UserException , CartltemExeption{
        User user = userService.findUserProfileByJwt(jwt);

        CartItem userCartIteam =cartltemService.updateCartIteam(user.getId(),cartIteamId,cartItem);
        return new ResponseEntity<>(userCartIteam,HttpStatus.ACCEPTED);
    }
    @PutMapping("/add")
    public ResponseEntity<String> addIteamToCart(@RequestBody AddIteamReq req , @RequestHeader("Authorization")String jwt)throws UserException , ProductExepton {
        User user =userService.findUserProfileByJwt(jwt);
        String msg=cartService.addCartIteam(user.getId(),req);


        return new ResponseEntity<>(msg, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/remove/{cartIteamId}")
    public ResponseEntity<String> removeCartIteam(@PathVariable Long cartIteamId,@RequestHeader("Authorization")String jwt)throws UserException,CartltemExeption{
        System.out.println("ID IS HERE :"+cartIteamId);
        User user=userService.findUserProfileByJwt(jwt);
        cartltemService.removeCartIteam(user.getId(),cartIteamId);

        return new ResponseEntity<>("CartIteam Remove successfully",HttpStatus.OK);
    }
}
