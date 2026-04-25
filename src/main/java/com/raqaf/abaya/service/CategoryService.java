package com.raqaf.abaya.service;

import com.raqaf.abaya.DTO.CategoryDto;
import com.raqaf.abaya.model.Category;
import com.raqaf.abaya.repo.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repo;

    // ➕ Add Category (ADMIN)
    public Category addCategory(CategoryDto dto) {

        if (repo.existsByName(dto.getName())) {
            throw new RuntimeException("Category already exists");
        }

        Category category = new Category();
        category.setName(dto.getName());

        return repo.save(category);
    }

    // 📄 Get All Categories
    public List<Category> getAll() {
        return repo.findAll();
    }
}