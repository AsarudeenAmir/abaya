package com.raqaf.abaya.controller;

import com.raqaf.abaya.DTO.CategoryDto;
import com.raqaf.abaya.model.Category;
import com.raqaf.abaya.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService service;

    @PostMapping
    public Category add(@RequestBody CategoryDto dto) {
        return service.addCategory(dto);
    }
}