package fr.orleans.m1.wsi.ecommerce.services;

import fr.orleans.m1.wsi.ecommerce.models.Product;
import fr.orleans.m1.wsi.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService
{
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> getAllProducts(){
        return productRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public Product getProduct(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produit non trouvé!"));
    }

    @Transactional
    public Product createdProduct(Product product){
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product product){
        Product existingProd = getProduct(id);
        existingProd.setName(product.getName());
        existingProd.setDescription(product.getDescription());
        existingProd.setPrice(product.getPrice());
        existingProd.setStock(product.getStock());
        existingProd.setImage(product.getImage());
        existingProd.setCategory(product.getCategory());

        return productRepository.save(existingProd);
    }
}
