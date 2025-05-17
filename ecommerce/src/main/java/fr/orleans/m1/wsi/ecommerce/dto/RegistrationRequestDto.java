package fr.orleans.m1.wsi.ecommerce.dto;

public record RegistrationRequestDto(
    String username,
    String password,
    String email
) {}
