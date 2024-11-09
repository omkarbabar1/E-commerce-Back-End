package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.exception.CartltemExeption;
import com.example.e_commerce_backend.exception.UserException;
import com.example.e_commerce_backend.model.Cart;
import com.example.e_commerce_backend.model.CartItem;
import com.example.e_commerce_backend.model.Product;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.repo.CartIteamRepo;
import com.example.e_commerce_backend.repo.CartRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartIteamSeriveImpl implements CartltemService{

    private CartIteamRepo cartIteamRepo;
    private UserService userService;
    private CartRepo cartRepo;

    @Override
    public CartItem createCartlteam(CartItem cartItem) {

        cartItem.setQuantity(cartItem.getQuantity());
        cartItem.setPrice(cartItem.getProduct().getPrice()* cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()* cartItem.getQuantity());
        CartItem createdCartIteam = cartIteamRepo.save(cartItem);
        System.out.println(createdCartIteam);
        return createdCartIteam;
    }

    @Override
    public CartItem updateCartIteam(Long userId, Long id, CartItem cartItem) throws CartltemExeption, UserException {
        // Fetch the cart item by ID
        CartItem iteam = findCartIteamById(id);
        if (iteam == null) {
            throw new CartltemExeption("Cart Item not found");
        }

        // Fetch the associated user
        User user = userService.findUserById(iteam.getUserId());
        if (user == null) {
            throw new UserException("User not found for the given cart item");
        }

        // Check if the user ID matches
        if (!user.getId().equals(userId)) {
            throw new CartltemExeption("Cart not found for the provided user");
        }

        if (cartItem.getProduct() != null) {
            iteam.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
            iteam.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
        } else {

            throw new CartltemExeption("Product information is required for updating the cart item");
        }

        return cartIteamRepo.save(iteam);
    }


    @Override
    public CartItem isCartItteamExist(Cart cart, Product product, String size, Long userId) {
        CartItem cartItem = cartIteamRepo.isCartltemExist(cart,product,size,userId);



        return cartItem;
    }

    @Override
    public void removeCartIteam(Long id, Long cartIteamId) throws CartltemExeption, UserException {

        CartItem cartItem = findCartIteamById(cartIteamId);

        User user = userService.findUserById(cartItem.getUserId());
        User user1 = userService.findUserById(id);

        if (user.getId().equals(user1.getId())){

            if (cartIteamRepo.existsById(cartIteamId)){
                cartIteamRepo.deleteById(cartIteamId);
                System.out.println("DELETING THAT MSGG");
            }
            System.out.println("NOT DELETE THAT MSG");
        }else
        throw new UserException("You can't remove anthor User Iteams ");
    }

    @Override
    public CartItem findCartIteamById(Long cartIteamId) throws CartltemExeption {
            return cartIteamRepo.findById(cartIteamId)
                    .orElseThrow(() -> new CartltemExeption("Cart not found with Cart Item Id: " + cartIteamId));
    }
}
