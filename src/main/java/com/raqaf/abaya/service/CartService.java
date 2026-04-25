package com.raqaf.abaya.service;

import com.raqaf.abaya.DTO.AddToCartDto;
import com.raqaf.abaya.model.Cart;
import com.raqaf.abaya.model.CartItem;
import com.raqaf.abaya.model.Product;
import com.raqaf.abaya.repo.CartItemRepository;
import com.raqaf.abaya.repo.CartRepository;
import com.raqaf.abaya.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;
    private final CartItemRepository itemRepo;
    private final ProductRepository productRepo;

    // 🔍 Get or create cart
    private Cart getOrCreateCart(Long userId) {
        return cartRepo.findByUserId(userId)
                .orElseGet(() -> cartRepo.save(new Cart(null, userId)));
    }

    // ➕ Add to cart
    public String addToCart(Long userId, AddToCartDto dto) {

        Cart cart = getOrCreateCart(userId);

        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem item = itemRepo
                .findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + dto.getQuantity());
        } else {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(dto.getQuantity());
        }

        itemRepo.save(item);
        return "Added to cart";
    }

    // 📄 View cart
    public List<CartItem> viewCart(Long userId) {

        Cart cart = getOrCreateCart(userId);

        return itemRepo.findByCartId(cart.getId());
    }

    // ✏️ Update quantity
    public String updateQuantity(Long userId, Long productId, Integer qty) {

        Cart cart = getOrCreateCart(userId);

        CartItem item = itemRepo
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setQuantity(qty);
        itemRepo.save(item);

        return "Updated";
    }

    // ❌ Remove item
    public String removeItem(Long userId, Long productId) {

        Cart cart = getOrCreateCart(userId);

        CartItem item = itemRepo
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        itemRepo.delete(item);

        return "Removed";
    }
}
