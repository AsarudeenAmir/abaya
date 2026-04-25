package com.raqaf.abaya.DTO;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
