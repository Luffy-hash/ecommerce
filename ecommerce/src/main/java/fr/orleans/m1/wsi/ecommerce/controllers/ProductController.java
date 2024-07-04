package fr.orleans.m1.wsi.ecommerce.controllers;

import fr.orleans.m1.wsi.ecommerce.models.Product;
import fr.orleans.m1.wsi.ecommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/product")
public class ProductController
{
    @Autowired
    private ProductService productService;

    /**
     *
     * @param product
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid Product product)
    {
        try {
            Product myProduct = productService.addedProduct(product);
            return new ResponseEntity<>(myProduct, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param product
     * @return
     */
    @PutMapping("/update/{idProduct}")
    public ResponseEntity<Product> updateProduct(@PathVariable @Valid Long idProduct, @RequestBody @Valid Product product)
    {
        try {
            Product updateProduct = productService.updatedProduct(idProduct, product);
            return new ResponseEntity<>(updateProduct, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param idDelete
     * @return
     */
    @DeleteMapping("/delete/{idDelete}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable @Valid Long idDelete)
    {
       try {
           productService.deletedProduct(idDelete);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       catch (Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(required = false, defaultValue = "0") int page,
                                                     @RequestParam(required = false, defaultValue = "5") int limit,
                                                     @RequestParam(required = false) String productName,
                                                     @RequestParam(required = false)Sort.Direction sortType) {
        try {
            Page<Product> pageProduct = productService.getRequestFilter(page, limit, productName, sortType);
            return new ResponseEntity<>(pageProduct, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
