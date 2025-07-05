package fr.orleans.m1.wsi.ecommerce.dto;

import java.util.Set;

public record RegistrationRequestDto(
    String username,
    String email,
    String password,
    Set<String> roles
) {}
