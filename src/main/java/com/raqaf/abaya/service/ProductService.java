package com.raqaf.abaya.service;

import com.raqaf.abaya.DTO.ProductDto;
import com.raqaf.abaya.model.Category;
import com.raqaf.abaya.model.Product;
import com.raqaf.abaya.repo.CategoryRepository;
import com.raqaf.abaya.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    // ➕ Add Product (ADMIN)
    public Product addProduct(ProductDto dto) {

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());
        product.setCategory(category);

        return productRepo.save(product);
    }

    // 📄 Get All Products
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    // 🔍 Get By Id
    public Product getProduct(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // ✏️ Update Product (ADMIN)
    public Product updateProduct(Long id, ProductDto dto) {

        Product product = getProduct(id);

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());

        return productRepo.save(product);
    }

    // ❌ Delete Product (ADMIN)
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
}
