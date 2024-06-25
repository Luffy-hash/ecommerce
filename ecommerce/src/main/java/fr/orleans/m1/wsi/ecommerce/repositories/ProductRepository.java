package fr.orleans.m1.wsi.ecommerce.repositories;

import fr.orleans.m1.wsi.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}
