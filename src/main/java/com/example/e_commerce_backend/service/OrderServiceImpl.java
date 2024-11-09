package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.exception.OrderException;
import com.example.e_commerce_backend.model.*;
import com.example.e_commerce_backend.repo.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    private OrderRepo orderRepo;
    private CartService cartService;
    private ProductService productService;
    private UserRepo userRepo;
    private AddressRepo addressRepo;
    private OrderItemService orderItemService;
    private OrderItemRepo orderItemRepo;

    @Override
    public Order createOrder(User user, Address shippingAdress) {

        shippingAdress.setUser(user);
        Address address = addressRepo.save(shippingAdress);
        user.getAddress().add(address);
        userRepo.save(user);

        Cart cart=cartService.findUserCart(user.getId());

        List<OrderIteam> orderIteams = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()){
            OrderIteam orderIteam = new OrderIteam();

            orderIteam.setPrice(cartItem.getPrice());
            orderIteam.setProduct(cartItem.getProduct());
            orderIteam.setQuantity(cartItem.getQuantity());
            orderIteam.setSize(cartItem.getSize());
            orderIteam.setUserId(cartItem.getUserId());
            orderIteam.setDicountedPrice(cartItem.getDiscountedPrice());

            OrderIteam createdOrderIteam = orderItemRepo.save(orderIteam);

            orderIteams.add(createdOrderIteam);
        }

        Order createdOrder=new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderIteams(orderIteams);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscounte(cart.getDiscounte());
        createdOrder.setTotalIteam(cart.getTotalltem());
        createdOrder.setShippingAdreess(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.getPaymentDetails().setStatus("PENDING");
        createdOrder.setCreatedAt(LocalDateTime.now());
        Order savedOrder=orderRepo.save(createdOrder);

        for (OrderIteam iteam : orderIteams){
            iteam.setOrder(savedOrder);
            orderItemRepo.save(iteam);
        }

        return savedOrder;
    }

    @Override
    public Order findOrderByld(Long orderld)throws OrderException  {

        Optional<Order> order = orderRepo.findById(orderld);

        if (order.isPresent()){
            return order.get();
        }

        throw new OrderException("Oreder Not Exist with given ID "+orderld);
    }

    @Override
    public List<Order> usersOrderHistory(Long userld) {

        List<Order> orders = orderRepo.getUsersOrders(userld);
        return orders;
    }

    @Override
    public Order placedOrder(Long orderld) throws OrderException {

        Order order = findOrderByld(orderld);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setStatus("COMPLETED");
        return order;
    }

    @Override
    public Order confirmedOrder(Long orderld) throws OrderException {

        Order order = new Order();
        order.setOrderStatus("CONFIRMED");
        return orderRepo.save(order);
    }

    @Override
    public Order shippedOrder(Long orderld) throws OrderException {

        Order order = findOrderByld(orderld);
        order.setOrderStatus("SHIPPED");
        return orderRepo.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderld) throws OrderException {

        Order order = findOrderByld(orderld);
        order.setOrderStatus("DELIVERED");
        return orderRepo.save(order);
    }

    @Override
    public Order cancledOrder(Long orderld) throws OrderException {
        Order order = findOrderByld(orderld);
        order.setOrderStatus("CANCELLED");

        return orderRepo.save(order);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepo.findAll();
    }

    @Override
    public String deleteById(Long orderId) {
        orderRepo.deleteById(orderId);
        return "Order Deleted Success Id "+orderId;
    }
}
