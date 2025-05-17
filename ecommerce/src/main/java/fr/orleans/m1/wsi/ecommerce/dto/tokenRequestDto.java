package fr.orleans.m1.wsi.ecommerce.dto;

public record tokenRequestDto(
    String accessToken
) {
    public tokenRequestDto {
        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalArgumentException("Access token cannot be null or empty");
        }
    }
}
