package fr.orleans.m1.wsi.ecommerce.dto;

import lombok.Data;

@Data
public class LoginRequest
{
    private String email;
    private String password;
}
