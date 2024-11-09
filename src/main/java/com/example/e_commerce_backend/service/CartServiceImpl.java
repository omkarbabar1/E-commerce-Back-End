package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.exception.CartltemExeption;
import com.example.e_commerce_backend.exception.ProductExepton;
import com.example.e_commerce_backend.model.Cart;
import com.example.e_commerce_backend.model.CartItem;
import com.example.e_commerce_backend.model.Product;
import com.example.e_commerce_backend.model.User;
import com.example.e_commerce_backend.repo.CartRepo;
import com.example.e_commerce_backend.request.AddIteamReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService{

    private CartRepo cartRepo;
    private CartltemService cartltemService;
    private ProductService productService;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);

        return cartRepo.save(cart);
    }

    @Override
    public String addCartIteam(Long userId, AddIteamReq req) throws ProductExepton {

        Cart cart = cartRepo.findUserById(userId);

        Product product = productService.findProductByid(req.getProductld());

        CartItem isPresent = cartltemService.isCartItteamExist(cart,product,req.getSize(),userId);

        if (isPresent==null){
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);
            int price = req.getQuantity()*product.getPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());
            int discountprice = req.getQuantity()*product.getDiscountedPrice();
            cartItem.setDiscountedPrice(discountprice);

            CartItem createdCartItem = cartltemService.createCartlteam(cartItem);
            cart.getCartItems().add(createdCartItem);
        }
        return "Item Add To Cart";
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepo.findUserById(userId);
        int totalPrice=0;
        int totalDiscountedPrice=0;
        int totalIteam=0;

        System.out.println("CartItems :"+cart.getCartItems());

        for (CartItem cartItem : cart.getCartItems()){
            System.out.println("HERE INFO 1:"+cartItem.getPrice()+cartItem.getDiscountedPrice()+cartItem.getQuantity());
            totalPrice += cartItem.getPrice();
            totalDiscountedPrice += cartItem.getDiscountedPrice();
            totalIteam += cartItem.getQuantity();
        }

        System.out.println("HERE INFO 2:"+totalPrice+totalDiscountedPrice+totalIteam);

        cart.setTotalltem(totalIteam);
        cart.setTotalPrice(totalPrice);
        cart.setDiscounte(totalPrice-totalDiscountedPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);

        return cart;
    }
}
