package fr.orleans.m1.wsi.ecommerce.controllers;

import fr.orleans.m1.wsi.ecommerce.models.Product;
import fr.orleans.m1.wsi.ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductControllers
{
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try{
            return ResponseEntity.ok(productService.getAllProducts());
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PostMapping
    public ResponseEntity<Product> createdProduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.createdProduct(product));
    }

}
