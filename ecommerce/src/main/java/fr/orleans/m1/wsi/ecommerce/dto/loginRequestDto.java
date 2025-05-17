package fr.orleans.m1.wsi.ecommerce.dto;

public record loginRequestDto(String email, String password) {
    public loginRequestDto {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }
}
