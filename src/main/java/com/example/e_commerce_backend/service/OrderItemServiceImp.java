package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.exception.OrderException;
import com.example.e_commerce_backend.model.*;
import com.example.e_commerce_backend.repo.AddressRepo;
import com.example.e_commerce_backend.repo.OrderItemRepo;
import com.example.e_commerce_backend.repo.OrderRepo;
import com.example.e_commerce_backend.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemServiceImp implements OrderItemService{

    private OrderItemRepo orderItemRepo;
    private AddressRepo addressRepo;
    private UserRepo userRepo;
    private CartService cartService;
    private OrderRepo orderRepo;


    @Override
    public Order createOrderItem(User user, Address shipaddress) throws OrderException {
        shipaddress.setUser(user);
        Address address = addressRepo.save(shipaddress);
        user.getAddress().add(address);
        userRepo.save(user);

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderIteam> orderIteams = new ArrayList<>();

        for(CartItem item: cart.getCartItems()) {
            OrderIteam orderltem=new OrderIteam();
            orderltem.setPrice(item.getPrice());
            orderltem.setProduct(item.getProduct());
            orderltem.setQuantity(item.getQuantity());
            orderltem.setSize(item.getSize());
            orderltem.setUserId(item.getUserId());
            orderltem.setDicountedPrice(item.getDiscountedPrice());

            OrderIteam orderIteam = orderItemRepo.save(orderltem);

            orderIteams.add(orderIteam);

        }
         Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setTotalIteam(cart.getTotalltem());
        createdOrder.setDiscounte(cart.getDiscounte());
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());

        createdOrder.setOrderStatus("PENDING");
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setOrderIteams(orderIteams);
        createdOrder.getPaymentDetails().setStatus("PENDING");
        createdOrder.setShippingAdreess(address);
        createdOrder.setCreatedAt(LocalDateTime.now());

        Order saveOrder=orderRepo.save(createdOrder);

        for (OrderIteam iteam : orderIteams ){
            iteam.setOrder(saveOrder);
            orderItemRepo.save(iteam);
        }



        return saveOrder;
    }
}
