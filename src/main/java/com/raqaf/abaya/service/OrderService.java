package com.raqaf.abaya.service;

import com.raqaf.abaya.model.Cart;
import com.raqaf.abaya.model.CartItem;
import com.raqaf.abaya.model.Order;
import com.raqaf.abaya.model.OrderItem;
import com.raqaf.abaya.repo.CartItemRepository;
import com.raqaf.abaya.repo.CartRepository;
import com.raqaf.abaya.repo.OrderItemRepository;
import com.raqaf.abaya.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;

    // 📦 Place Order
    public String placeOrder(Long userId) {

        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> items = cartItemRepo.findByCartId(cart.getId());

        if (items.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());

        double total = 0;

        order = orderRepo.save(order);

        for (CartItem item : items) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getProduct().getPrice());

            total += item.getQuantity() * item.getProduct().getPrice();

            orderItemRepo.save(orderItem);
        }

        order.setTotalAmount(total);
        orderRepo.save(order);

        // 🧹 Clear cart
        cartItemRepo.deleteAll(items);

        return "Order placed successfully";
    }

    // 📄 User Orders
    public List<Order> getUserOrders(Long userId) {
        return orderRepo.findByUserId(userId);
    }

    // 👑 Admin: All Orders
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
}
