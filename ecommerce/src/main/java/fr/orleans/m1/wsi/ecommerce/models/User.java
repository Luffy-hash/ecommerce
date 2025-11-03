package fr.orleans.m1.wsi.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    @Column(nullable = false)
    private String role = "USER";

    private LocalDateTime createdAt = LocalDateTime.now();

}
