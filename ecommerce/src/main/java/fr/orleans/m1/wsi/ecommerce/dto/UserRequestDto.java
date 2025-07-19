package fr.orleans.m1.wsi.ecommerce.dto;


public record UserRequestDto(
    String email,
    String username,
    String password,
    String phoneNumber
) {}