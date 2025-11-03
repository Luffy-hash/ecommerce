package fr.orleans.m1.wsi.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse
{
    private String token;
    private UserDTO userDTO;

    @Data
    @AllArgsConstructor
    public static class UserDTO{
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
    }
}
