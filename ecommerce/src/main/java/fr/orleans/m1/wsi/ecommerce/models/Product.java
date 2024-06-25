package fr.orleans.m1.wsi.ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Ce champs est requis.")
    private String name;

    @NotNull(message = "Ce champs est requis.")
    private String image;

    @NotNull(message = "Ce champs est requis.")
    private Long price;

    @NotNull(message = "Ce champs est requis.")
    private Long quantity;

    private String description;


}
