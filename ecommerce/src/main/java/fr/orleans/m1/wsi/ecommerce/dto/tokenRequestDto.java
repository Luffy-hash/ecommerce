package fr.orleans.m1.wsi.ecommerce.dto;

import java.util.List;

public record tokenRequestDto(
    String accessToken,
    String username,
    List<String> roles
) {}
