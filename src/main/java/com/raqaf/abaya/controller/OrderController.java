package com.raqaf.abaya.controller;

import com.raqaf.abaya.model.Order;
import com.raqaf.abaya.service.OrderService;
import com.raqaf.abaya.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;
    private final AuthUtil authUtil;

    @PostMapping
    public String placeOrder() {
        return service.placeOrder(authUtil.getCurrentUserId());
    }

    @GetMapping
    public List<Order> getMyOrders() {
        return service.getUserOrders(authUtil.getCurrentUserId());
    }
}
