package com.raqaf.abaya.controller;

import com.raqaf.abaya.DTO.ProductDto;
import com.raqaf.abaya.model.Product;
import com.raqaf.abaya.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService service;

    @PostMapping
    public Product add(@RequestBody ProductDto dto) {
        return service.addProduct(dto);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody ProductDto dto) {
        return service.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteProduct(id);
        return "Deleted successfully";
    }
}