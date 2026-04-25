package com.raqaf.abaya.controller;

import com.raqaf.abaya.model.Order;
import com.raqaf.abaya.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService service;

    @GetMapping
    public List<Order> getAll() {
        return service.getAllOrders();
    }
}
