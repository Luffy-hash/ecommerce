package fr.orleans.m1.wsi.ecommerce.services.serviceImpls;

import fr.orleans.m1.wsi.ecommerce.models.Product;
import fr.orleans.m1.wsi.ecommerce.repositories.ProductRepository;
import fr.orleans.m1.wsi.ecommerce.services.ProductService;
import fr.orleans.m1.wsi.ecommerce.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService
{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addedProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updatedProduct(Long idProduct, Product product) throws NotFoundException {
        Optional<Product> myProduct = productRepository.findById(idProduct);
        if (myProduct.isPresent()){
            Product productUpdate = myProduct.get();
            productUpdate.setName(product.getName());
            productUpdate.setImage(product.getImage());
            productUpdate.setPrice(product.getPrice());
            productUpdate.setQuantity(product.getQuantity());
            productUpdate.setDescription(product.getDescription());

            return productRepository.save(productUpdate);
        }
        else {
            throw  new NotFoundException("404 NOT FOUND");
        }
    }

    @Override
    public void deletedProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> getRequestFilter(int page, int limit, String productName, Sort.Direction sortType) {

        Page<Product> pageProduct = null;

        if (productName == null && sortType == null){
            pageProduct = getProductList(page, limit);
        }
        if (productName != null && sortType == null){
            pageProduct = getFindProductByName(page, limit, productName);
        }
        if (productName == null && sortType != null){
            pageProduct = getProductOrderByPrice(page, limit, sortType);
        }
        if (productName != null && sortType != null){
            pageProduct = findProductByNameAndOrderByPrice(page, limit, productName, sortType);
        }


        return pageProduct;
    }

    private Page<Product> findProductByNameAndOrderByPrice(int page, int limit, String productName, Sort.Direction sortType) {
        // Sort sort = Sort.by(sortType, "price");
        Pageable pageable = PageRequest.of(page, limit, sortType);
        return productRepository.findByNameContainingIgnoreCase(productName, pageable);
    }

    private Page<Product> getProductOrderByPrice(int page, int limit, Sort.Direction sortType) {
        Sort sort = Sort.by(sortType, "price");
        Pageable pageable = PageRequest.of(page, limit, sort);
        return productRepository.findAll(pageable);
    }

    private Page<Product> getFindProductByName(int page, int limit, String productName) {
        Pageable pageable = PageRequest.of(page, limit);
        return productRepository.findByNameContainingIgnoreCase(productName, pageable);
    }

    private Page<Product> getProductList(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return productRepository.findAll(pageable);
    }


}
