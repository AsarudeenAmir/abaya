package com.raqaf.abaya.controller;

import com.raqaf.abaya.DTO.AddToCartDto;
import com.raqaf.abaya.model.CartItem;
import com.raqaf.abaya.repo.UserRepository;
import com.raqaf.abaya.service.CartService;
import com.raqaf.abaya.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;
    private final UserRepository userRepo;
    private final AuthUtil authUtil;

    private Long getUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByEmail(email).get().getId();
    }

    @PostMapping("/add")
    public String add(@RequestBody AddToCartDto dto) {
        return service.addToCart(authUtil.getCurrentUserId(), dto);
    }

    @GetMapping
    public List<CartItem> view() {
        return service.viewCart(authUtil.getCurrentUserId());
    }

    @PutMapping("/update")
    public String update(@RequestParam Long productId,
                         @RequestParam Integer quantity) {
        return service.updateQuantity(getUserId(), productId, quantity);
    }

    @DeleteMapping("/remove")
    public String remove(@RequestParam Long productId) {
        return service.removeItem(getUserId(), productId);
    }
}
