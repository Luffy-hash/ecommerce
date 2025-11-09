package fr.orleans.m1.wsi.ecommerce.repositories;

import fr.orleans.m1.wsi.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    List<Product> findProductByCategory(String category);
    List<Product> findByActiveTrue();
}
