package com.raqaf.abaya.DTO;

import lombok.Data;

@Data
public class AddToCartDto {
    private Long productId;
    private Integer quantity;
}
