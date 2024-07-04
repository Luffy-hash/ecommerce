package fr.orleans.m1.wsi.ecommerce.services;

import fr.orleans.m1.wsi.ecommerce.models.Product;
import fr.orleans.m1.wsi.ecommerce.services.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public interface ProductService
{
    /**
     * this method add a product in a table
     * @param product
     * @return
     */
    Product addedProduct(Product product);

    /**
     *  this method update a product in a table
     * @param idProduct
     * @param product
     * @return
     */
    Product updatedProduct(Long idProduct, Product product) throws NotFoundException;

    /**
     *
     * @param id
     */
    void deletedProduct(Long id);

    /**
     *
     * @param page
     * @param limit
     * @param productName
     * @param sortType
     * @return
     */
    Page<Product> getRequestFilter (int page, int limit, String productName, Sort.Direction sortType);

}
