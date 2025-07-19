package fr.orleans.m1.wsi.ecommerce.dto;

import fr.orleans.m1.wsi.ecommerce.models.ERole;

public record RoleRequestDto(
    ERole roleName
) {}
